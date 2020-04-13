package com.erwin.tecnoagenda.Maps.SearchLocationActivity

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.erwin.tecnoagenda.Models.MapLocationModel
import com.erwin.tecnoagenda.R
import com.erwin.tecnoagenda.SearchLocationActivityViewModel
import com.erwin.tecnoagenda.databinding.ActivitySearchLocationBinding
import com.google.android.material.snackbar.Snackbar

class SearchLocationActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySearchLocationBinding
    private lateinit var viewModel:SearchLocationActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_search_location)

        setSupportActionBar(binding.searchLocationToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = null



        //ViewModelProvider.AndroidViewModelFactory()

        viewModel= ViewModelProvider(this).get(SearchLocationActivityViewModel::class.java)

//        viewModel.allLocationMap.observe(this, Observer {locations->
//
//            setText(locations)
//
//        })


        listenerTextInputSerchView()


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
    val navControllerMap=findNavController(R.id.searchLocationNavHostFragment)


        binding.searchviewLocationItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText=="") navControllerMap.navigate(R.id.allLocationsMapFragment)
                else navControllerMap.navigate(R.id.searchLocationMap)



                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }


}
