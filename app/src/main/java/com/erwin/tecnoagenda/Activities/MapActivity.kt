package com.erwin.tecnoagenda.Activities

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Explode
import android.view.*
import android.widget.TextView

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import com.erwin.tecnoagenda.Fragments.SearchLocationMap
import com.erwin.tecnoagenda.R
import com.erwin.tecnoagenda.Utils.snackBar

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_maps.*
import org.w3c.dom.Text
import java.lang.Exception

class MapActivity : AppCompatActivity(), OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener{


    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        setSupportActionBar(map_toolbar)
        supportActionBar?.title = null
        //toast("hola desde el toast")
        //configurations for navigation  drawer

        //get configurations of the navigationView
        val supActionBar=supportActionBar

        search_location_map_activity.setOnClickListener { startActivity(Intent(this,Search_map_window::class.java))}




        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //        menuInflater.inflate(R.menu.menu_toolbar, menu)

        val toggle=ActionBarDrawerToggle(this,map_drawer_layout,map_toolbar,R.string.open_drawer,R.string.close_drawer)
        toggle.isDrawerIndicatorEnabled=true
        map_drawer_layout.addDrawerListener(toggle)
        toggle.syncState() //sincroniza los estados
        navigation.setNavigationItemSelectedListener(this)


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
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    //items for select(To change body of created functions use File | Settings | File Templates.)
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
       return true
    }



}
