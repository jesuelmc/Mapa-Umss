package com.erwin. tecnoagenda.Maps.SearchLocationActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.erwin.tecnoagenda.Models.MapLocationModel
import com.erwin.tecnoagenda.R
import com.erwin.tecnoagenda.SearchLocationActivityViewModel
import com.erwin.tecnoagenda.Utils.snackBar
import com.erwin.tecnoagenda.databinding.ActivitySearchLocationBinding

class SearchLocationActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySearchLocationBinding
    private lateinit var viewModel:SearchLocationActivityViewModel

    //navigate on backpresed
    private var nav=true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_search_location)

        setSupportActionBar(binding.searchLocationToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = null

        //ViewModelProvider.AndroidViewModelFactory()

        viewModel= ViewModelProvider(this).get(SearchLocationActivityViewModel::class.java)

        listenerTextInputSerchView()



        //soluciono el problema de que el teclado ocultaba el fragmento
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home ->{ onBackPressed()
                true}
            else -> super.onOptionsItemSelected(item)
        }
    }
    fun setText(locations: List<MapLocationModel>){
//        Snackbar.make(binding.myCoordinatorLayout,locations[0].name,Snackbar.LENGTH_LONG)

    }


    fun listenerTextInputSerchView(){

        //val x= SearchView.OnQueryTextListener
        val navController=findNavController(R.id.searchLocationNavHostFragment)


        binding.searchviewLocationItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {

                if(newText=="") {
                    nav=true
                    onBackPressed()
                }
                else{
                    snackBar(newText!!,findViewById(R.id.searchview_location_item))
                    viewModel.searchViewText.value=newText
                    if(nav){
                        nav=false
                        navController.navigate(R.id.searchLocationMapFragment)
                    }
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
               return false
            }


        })
    }
    override fun onBackPressed() {
        if (!nav) binding.searchviewLocationItem.setQuery("",false)
        else super.onBackPressed()
    }


}
