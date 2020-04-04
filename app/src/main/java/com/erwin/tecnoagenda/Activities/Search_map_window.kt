package com.erwin.tecnoagenda.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.erwin.tecnoagenda.R
import com.erwin.tecnoagenda.Utils.snackBar
import kotlinx.android.synthetic.main.activity_search_map_window.*

class Search_map_window : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_map_window)

        setSupportActionBar(search_location_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home ->{ onBackPressed()
                true}
            else -> super.onOptionsItemSelected(item)
        }
    }

}
