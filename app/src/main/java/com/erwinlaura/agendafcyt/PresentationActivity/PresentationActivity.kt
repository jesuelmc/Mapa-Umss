package com.erwinlaura.agendafcyt.PresentationActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import com.erwinlaura.agendafcyt.R
import com.erwinlaura.agendafcyt.Utils.snackBar
import com.erwinlaura.agendafcyt.databinding.MapActivityPresentationBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PresentationActivity : AppCompatActivity() {

    private lateinit var binding: MapActivityPresentationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.map_activity_presentation)
        getFirestoreData()
    }
    private fun getFirestoreData(){

        var dbFirestore = FirebaseFirestore.getInstance()

        //val alltypeMapLocations=dbFirestore.collection("Locations").document("Types")

        val locationsCache = dbFirestore.collection("Locations")

        lifecycleScope.launch {
            var queryServer = locationsCache.get(Source.SERVER)
            while (true) {
                delay(2000)
                if (queryServer.isSuccessful) {
                    break
                } else {
                    snackBar("Contectate a internet solo en esta ocacion", binding.root)
                    delay(2000)
                }
                queryServer = locationsCache.get(Source.SERVER)
            }
            PreferenceManager.getDefaultSharedPreferences(this@PresentationActivity).edit().apply {
                putBoolean(PresentationFragment.COMPLETED_PRESENTATION, true)
                apply()
            }
            finish()
        }

    }
}
