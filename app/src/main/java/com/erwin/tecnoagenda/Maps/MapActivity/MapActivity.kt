package com.erwin.tecnoagenda.Maps.MapActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import com.erwin.tecnoagenda.Maps.SearchLocationActivity.SearchLocationActivity
import com.erwin.tecnoagenda.R
import com.erwin.tecnoagenda.databinding.ActivityMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.material.navigation.NavigationView

class MapActivity : AppCompatActivity(), OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener{


    private lateinit var mMap: GoogleMap
    private lateinit var binding:ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=DataBindingUtil.setContentView(this,R.layout.activity_maps)


        setSupportActionBar(binding.mapToolbar)
        supportActionBar?.title = null
        //toast("hola desde el toast")
        //configurations for navigation  drawer

        //get configurations of the navigationView
        val supActionBar=supportActionBar





        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)

        val toggle=ActionBarDrawerToggle(this,binding.mapDrawerLayout,binding.mapToolbar,R.string.open_drawer,R.string.close_drawer)
        toggle.isDrawerIndicatorEnabled=true
        binding.mapDrawerLayout.addDrawerListener(toggle)
        toggle.syncState() //sincroniza los estados
        binding.navigation.setNavigationItemSelectedListener(this)


        return true
    }

    private fun showWindowSearch(item: MenuItem){

        /*val fragmentManager=supportFragmentManager
        val fragmentTransaction= fragmentManager.beginTransaction()
        val fragment = SearchLocationMap()
        fragmentTransaction.add(R.id.myCoordinatorLayout,fragment)
        fragmentTransaction.commit()*/
    }


    //configuration for navigation drawer


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))


        val campusCentral = LatLng(-17.39356623953956,-66.14553816328916)
        //gMap.addMarker(MarkerOptions().position(campusCentral).title("Marker in Sydney"))

        val camera: CameraPosition? =
            CameraPosition.builder().target(campusCentral).zoom(18F).bearing(0F).tilt(90F).build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera))
        //gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(campusCentral,18F))
        mMap.setMaxZoomPreference(18F)

        val limit = LatLngBounds(LatLng(-17.39307480327583,-66.14951160041626), LatLng(-17.39173972730723,-66.14224269812405))
        mMap.setLatLngBoundsForCameraTarget(limit)

    }

    //items for select(To change body of created functions use File | Settings | File Templates.)
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
       return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.map_search_item -> {
                startActivity(Intent(this, SearchLocationActivity::class.java))
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }




}
