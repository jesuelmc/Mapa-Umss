package com.erwinlaura.agendafcyt.Maps.MapActivity

import android.app.Activity
import android.content.Intent
import android.net.Uri
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
import com.erwinlaura.agendafcyt.databinding.MapActivityMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.Source
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import java.util.*

class MapActivity : AppCompatActivity(), OnMapReadyCallback,
    NavigationView.OnNavigationItemSelectedListener {


    private lateinit var mMap: GoogleMap
    private lateinit var binding: MapActivityMapsBinding
    private lateinit var viewModel: MapActivityViewModel
    private lateinit var sheetBehavior: BottomSheetBehavior<View>
    private lateinit var idDocumentFirestore: String


    companion object {
        const val REQUEST_CODE_SEARCHLOCATION = 3243
        const val REQUEST_CODE_GET_IMAGE = 234
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //for get data from firestore
        checkIfFirstExecution()

        binding = DataBindingUtil.setContentView(this, R.layout.map_activity_maps)
        setSupportActionBar(binding.mapToolbar)
        //supportActionBar?.title = null

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        bottomSheetBehavior()

        listenerGetImageOfGallery()

        viewModel = ViewModelProvider(this).get(MapActivityViewModel::class.java)


    }

    private fun listenerGetImageOfGallery() {
        binding.buttonGetImage.setOnClickListener {

            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE_GET_IMAGE)

        }
    }

    private fun checkIfFirstExecution() {
        PreferenceManager.getDefaultSharedPreferences(this).apply {
            //Is first execution?
            if (!getBoolean(PresentationFragment.COMPLETED_PRESENTATION, false)) {
                startActivity(Intent(this@MapActivity, PresentationActivity::class.java))
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menu?.let {
            inflateNavigationDrawer(it)
        }

        return true
    }

    private fun inflateNavigationDrawer(menu: Menu) {
        menuInflater.inflate(R.menu.map_menu_toolbar, menu)
        val toggle = ActionBarDrawerToggle(
            this,
            binding.mapDrawerLayout,
            binding.mapToolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )
        toggle.isDrawerIndicatorEnabled = true
        binding.mapDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.navigation.setNavigationItemSelectedListener(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val campusCentral = LatLng(-17.39356623953956, -66.14553816328916)
        val camera: CameraPosition? = CameraPosition.builder()
            .target(campusCentral)
            .zoom(18F)
            .bearing(350F)
            .tilt(30F)
            .build()
        mMap.moveCamera(
            CameraUpdateFactory.newCameraPosition(camera)
        )
        mMap.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(this, R.raw.style_map_json)
        )
        mMap.setMinZoomPreference(18F)
        val limit = LatLngBounds(
            LatLng(-17.396785117962597, -66.149278920572),
            LatLng(-17.39122576220317, -66.1421862396757)
        )
        mMap.setLatLngBoundsForCameraTarget(limit)

    }

    //items for select(To change body of created functions use File | Settings | File Templates.)
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.map_search_item -> {
                startActivityForResult(
                    Intent(this, SearchLocationActivity::class.java),
                    REQUEST_CODE_SEARCHLOCATION
                )
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }

    }

    private fun showLocation() {
        lifecycle.coroutineScope.launch {
            viewModel.dbFirestore.collection("Locations")
                .document(idDocumentFirestore)
                .get(Source.CACHE)
                .addOnSuccessListener {

                    mMap.clear()
                    binding.buttonGetImage.visibility = View.GONE

                    markerAndMoveCamera(it)

                    confBottomSheetBehavior(it)
                }
        }
    }

    private fun confBottomSheetBehavior(locationDocument: DocumentSnapshot) {
        binding.textViewBottom.text = locationDocument.data!!["name"].toString()

        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)

        loadImagesBottomSheetBehavior()
    }

    private fun loadImagesBottomSheetBehavior() {
        val imageContainer: LinearLayout = binding.imageContainerMap
        imageContainer.removeAllViews()

        val imageContainerParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(250, 200)

        viewModel.dbFirestore.collection("Locations")
            .document(idDocumentFirestore)
            .collection("Images")
            .document("images")
            .addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                var i = 0
                documentSnapshot?.data?.forEach {
                    if (it.value.toString() != "") {
                        i++
                        imageContainerParams.setMargins(20, 20, 20, 20)
                        imageContainerParams.gravity = Gravity.CENTER
                        val imageView = ImageView(this)
                        imageView.layoutParams = imageContainerParams
                        Picasso.get().load(it.value.toString()).fit().into(imageView)
                        imageContainer.addView(imageView)
                    }
                }
                if (!imageContainerIsFull(i)) binding.buttonGetImage.visibility = View.VISIBLE
            }

    }


    private fun markerAndMoveCamera(locationDocument: DocumentSnapshot) {
        val locationMap = locationDocument.data!!
        val latitude = (locationMap["geopoint"] as GeoPoint).latitude
        val longitude = (locationMap["geopoint"] as GeoPoint).longitude
        val location = LatLng(latitude, longitude)
        val name = locationMap["name"].toString()

        mMap.addMarker(
            MarkerOptions().position(location).title(name)
        ).showInfoWindow()

        val camera: CameraPosition? =
            CameraPosition.builder()
                .target(location)
                .zoom(18F)
                .bearing(350F)
                .tilt(30F)
                .build()

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera))


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                when (requestCode) {

                    REQUEST_CODE_SEARCHLOCATION -> {
                        idDocumentFirestore = data.getStringExtra("id")!!
                        showLocation()
                    }

                    REQUEST_CODE_GET_IMAGE -> uploadImage(data)

                }
            }
        }
    }

    private fun uploadImage(data: Intent) {
        //snackBar("Se subio su imagen correctamente ", binding.mapToolbar)

        val selectedImage = data.data
        if (selectedImage != null) {

            binding.progressBarUploadImage.visibility = View.VISIBLE
            binding.buttonGetImage.isEnabled = false
            val path =
                "imagenesMapa/" + UUID.randomUUID() + selectedImage.lastPathSegment
            val riversRef = viewModel.storageRef.child(path)
            val uploadTask = riversRef.putFile(selectedImage)

            uploadTask.addOnCompleteListener {
                binding.progressBarUploadImage.visibility = View.GONE
                binding.buttonGetImage.isEnabled = true
            }

            val getDownloadUriTask = uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let { throw it }
                }
                riversRef.downloadUrl
            }

            updateFirestore(getDownloadUriTask)
        }
    }

    private fun updateFirestore(getDownloadUriTask: Task<Uri>) {

        getDownloadUriTask.addOnCompleteListener { task ->
            //xif (task.isSuccessful) {
            val downloadUri = task.result

            val images = viewModel.dbFirestore
                .collection("Locations")
                .document(idDocumentFirestore)
                .collection("Images")
                .document("images")

            viewModel.dbFirestore.collection("Locations")
                .document(idDocumentFirestore)
                .collection("Images")
                .document("images")
                .get(Source.SERVER).addOnSuccessListener {
                    for (document in it?.data?.toList()!!) {
                        if (fieldUrlIsFree(document.second)) {
                            images.update(
                                document.first.toString(),
                                downloadUri.toString()
                            )
                            break
                        }
                    }
                }

        }
    }


    private fun bottomSheetBehavior() {
        //val coordinatorLayou:CoordinatorLayout=binding.myCoordinatorLayout

        val bottom_sheet = binding.buttomSheetMap
        sheetBehavior = BottomSheetBehavior.from(bottom_sheet)
        sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

//        sheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
//            override fun onStateChanged(bottomSheet: View, newState: Int) {
//
//            }
//
//            override fun onSlide(bottomSheet: View, slideOffset: Float) {
//            }
//        })
    }


    private fun fieldUrlIsFree(fieldUrl: Any?): Boolean =
        (fieldUrl == null || fieldUrl.toString() == "")

    private fun imageContainerIsFull(i: Int): Boolean = (i >= 5)


}


