package com.erwinlaura.agendafcyt.Maps.MapActivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import androidx.preference.PreferenceManager
import com.erwinlaura.agendafcyt.Maps.SearchLocationActivity.SearchLocationActivity
import com.erwinlaura.agendafcyt.PresentationActivity.PresentationActivity
import com.erwinlaura.agendafcyt.PresentationActivity.PresentationFragment
import com.erwinlaura.agendafcyt.R
import com.erwinlaura.agendafcyt.Utils.snackBar
import com.erwinlaura.agendafcyt.databinding.ActivityMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.Source
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import java.util.*

class MapActivity : AppCompatActivity(), OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener{


    private lateinit var mMap: GoogleMap
    private lateinit var binding:ActivityMapsBinding

    private lateinit var viewModel: MapActivityViewModel

    private lateinit var sheetBehavior:BottomSheetBehavior<View>

    private lateinit var idDocumentFirestore:String


    companion object {
        val REQUEST_CODE_SEARCHLOCATION = 3243
        val REQUEST_CODE_GET_IMAGE=234
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=DataBindingUtil.setContentView(this,R.layout.activity_maps)

        PreferenceManager.getDefaultSharedPreferences(this).apply {
            if(!getBoolean(PresentationFragment.COMPLETED_PRESENTATION,false)){
                startActivity(Intent(this@MapActivity,PresentationActivity::class.java))
            }
        }


        setSupportActionBar(binding.mapToolbar)
        //supportActionBar?.title = null
        //toast("hola desde el toast")
        //configurations for navigation  drawer

        //get configurations of the navigationView
        val supActionBar=supportActionBar


        //snackBar("se inicio",binding.mapToolbar)



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)


        buttoBehabior()
        binding.buttonGetImage.setOnClickListener {

            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE_GET_IMAGE)

        }
        viewModel=ViewModelProvider(this).get(MapActivityViewModel::class.java)


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
        val fragment = SearchLocationMapFragment()
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
            CameraPosition.builder().target(campusCentral).zoom(18F).bearing(350F).tilt(30F).build()
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(camera))

        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,R.raw.style_map_json))
        //gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(campusCentral,18F))
        mMap.uiSettings.isCompassEnabled=true
        mMap.setMinZoomPreference(18F) 

        val limit = LatLngBounds(LatLng(-17.396785117962597,-66.149278920572), LatLng(-17.39122576220317,-66.1421862396757))
        mMap.setLatLngBoundsForCameraTarget(limit)

    }

    //items for select(To change body of created functions use File | Settings | File Templates.)
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
       return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.map_search_item -> {
                startActivityForResult(Intent(this, SearchLocationActivity::class.java),REQUEST_CODE_SEARCHLOCATION)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }

    }

    private fun showLocation(locationDocument:DocumentSnapshot){


        binding.buttonGetImage.visibility=View.GONE
        val locationMap=locationDocument.data!!
        mMap.clear()
        val latitude=(locationMap["geopoint"] as GeoPoint).latitude
        val longitude=(locationMap["geopoint"] as GeoPoint).longitude
        val location=LatLng(latitude,longitude)
        val name=locationMap["name"].toString()

        mMap.addMarker(MarkerOptions().position(location).title(name)).showInfoWindow()

//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,18F))

        val camera: CameraPosition? = CameraPosition.builder().target(location).zoom(18F).bearing(350F).tilt(30F).build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera))



        binding.textViewBottom.text=name

//        if (sheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
//        }
//        else {
            //sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        //}
        //binding.buttomSheetMap.visibility=View.VISIBLE
        //viewModel.onShowLocationComplete()

        val imageContainer:LinearLayout=binding.imageContainerMap

        imageContainer.removeAllViews()

        val imageContainerParams:LinearLayout.LayoutParams=LinearLayout.LayoutParams(250,200)


        //val imagesMap=locationMap["images"] as List<*>

        viewModel.dbFirestore.collection("Locations")
            .document(idDocumentFirestore)
            .collection("Images")
            .document("images")
            .addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                var i=0
                documentSnapshot?.data?.forEach {
                    if(it.value.toString()!="") {
                        i++
                        imageContainerParams.setMargins(20, 20, 20, 20)
                        imageContainerParams.gravity = Gravity.CENTER
                        val imageView = ImageView(this)
                        imageView.layoutParams = imageContainerParams
                        Picasso.get().load(it.value.toString()).fit().into(imageView)
                        imageContainer.addView(imageView)
                    }
                }
                if(i<5) binding.buttonGetImage.visibility=View.VISIBLE
            }

//        imagesMap.forEach {
//            imageContainerParams.setMargins(20,20,20,20)
//            imageContainerParams.gravity=Gravity.CENTER
//            val imageView = ImageView(this)
//            imageView.layoutParams=imageContainerParams
//            Picasso.get().load(it.toString()).fit().into(imageView)
//        }
//
//        for(i in 1 .. 4){
//            imageContainerParams.setMargins(20,20,20,20)
//            imageContainerParams.gravity=Gravity.CENTER
//            val imageView = ImageView(this)
//
//            imageView.setImageResource(R.drawable.menu_header)
//            imageView.layoutParams=imageContainerParams
//            imageContainer.addView(imageView)
//        }



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode== Activity.RESULT_OK){
            if (data != null) {
                when (requestCode) {
                    REQUEST_CODE_SEARCHLOCATION -> {
                        idDocumentFirestore=data.getStringExtra("id")!!
                        lifecycle.coroutineScope.launch {
//                            val x = withContext(Dispatchers.Default) {
//                                viewModel.getLocation((data.getStringExtra("id"))!!.toInt())
//                            }
//                            //viewModel.setLocation((data.getStringExtra("id"))!!.toInt())
//                            showLocation(x)

                            viewModel.dbFirestore.collection("Locations")
                                .document(idDocumentFirestore)
                                .get(Source.CACHE)
                                .addOnSuccessListener {
                                   // val location= it.data
                                    showLocation(it)
                            }


                        }

                    }

                    REQUEST_CODE_GET_IMAGE -> {

                        snackBar("Se subio su imagen correctamente ",binding.mapToolbar)
                        val selectedImage = data.data
                        if (selectedImage != null) {
                            binding.progressBarUploadImage.visibility=View.VISIBLE
                            binding.buttonGetImage.isEnabled=false
                            val path = "imagenesMapa/" + UUID.randomUUID() + selectedImage.lastPathSegment
                            val riversRef = viewModel.storageRef.child(path)
                            val uploadTask = riversRef.putFile(selectedImage)

                            uploadTask.addOnCompleteListener {
                                binding.progressBarUploadImage.visibility=View.GONE
                                binding.buttonGetImage.isEnabled=true
                            }
                            val getDownloadUriTask=uploadTask.continueWithTask{ task ->
                                if (!task.isSuccessful) {
                                    task.exception?.let { throw it }
                                }
                                //snackBar(riversRef.downloadUrl.toString(),binding.mapToolbar,Snackbar.LENGTH_LONG)
                                riversRef.downloadUrl
                            }

                            getDownloadUriTask.addOnCompleteListener { task ->
                                //xif (task.isSuccessful) {
                                    val downloadUri = task.result

                                    val images=viewModel.dbFirestore
                                        .collection("Locations")
                                        .document(idDocumentFirestore).collection("Images").document("images")

                                    viewModel.dbFirestore.collection("Locations")
                                        .document(idDocumentFirestore)
                                        .collection("Images")
                                        .document("images")
                                        .get(Source.SERVER).addOnSuccessListener {
                                            for(document in it?.data?.toList()!!){
                                                if(document.second==null || document.second.toString()==""){
                                                    images.update(document.first.toString(),downloadUri.toString())
                                                    break
                                                }
                                            }

//                                            documentSnapshot?.data?.forEach {
//                                                if(it.component2()==null || it.component2().toString()==""){
//                                                    images.update(it.key.toString(),downloadUri.toString())
//
//                                                }
//                                            }
                                        }
//                                    location.update("urlImage1",downloadUri.toString())
                                    //snackBar("se ejecuto el ultimo",binding.mapToolbar)
                                //}
                            }
                        }
                    }

                }
            }
        }
    }


    fun buttoBehabior(){
        //val coordinatorLayou:CoordinatorLayout=binding.myCoordinatorLayout


        val bottom_sheet=binding.buttomSheetMap
        sheetBehavior=BottomSheetBehavior.from(bottom_sheet)
        sheetBehavior.state=BottomSheetBehavior.STATE_HIDDEN


        sheetBehavior.addBottomSheetCallback( object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }
}


