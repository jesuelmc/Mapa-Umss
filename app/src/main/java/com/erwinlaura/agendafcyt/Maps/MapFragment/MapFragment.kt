package com.erwinlaura.agendafcyt.Maps.MapFragment


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import com.erwinlaura.agendafcyt.MainActivity
import com.erwinlaura.agendafcyt.MainActivity.Companion.REQUEST_CODE_SEARCHLOCATION
import com.erwinlaura.agendafcyt.Maps.SearchLocationActivity.SearchLocationActivity

import com.erwinlaura.agendafcyt.databinding.FragmentMapBinding
import com.erwinlaura.agendafcyt.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.Source
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import java.util.*


class MapFragment : Fragment() ,OnMapReadyCallback{


    private lateinit var binding:FragmentMapBinding

    private lateinit var mMap:GoogleMap
    private lateinit var mapView:MapView

    private lateinit var sheetBehavior: BottomSheetBehavior<View>
    private lateinit var idDocumentFirestore: String

    private lateinit var viewModel:MapFragmentViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_map, container, false)

        viewModel=ViewModelProvider(this).get(MapFragmentViewModel::class.java)

        bottomSheetBehavior()

        listenerGetImageOfGallery()



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView=binding.map
        //aqui en vez de null se puede pasar un dato
        mapView.onCreate(null)
        mapView.onResume()
        mapView.getMapAsync(this)


    }

    override fun onMapReady(p0: GoogleMap?) {
        mMap= p0!!

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
            MapStyleOptions.loadRawResourceStyle(activity, R.raw.style_map_json)
        )
        mMap.setMinZoomPreference(18F)
        val limit = LatLngBounds(
            LatLng(-17.396785117962597, -66.149278920572),
            LatLng(-17.39122576220317, -66.1421862396757)
        )
        mMap.setLatLngBoundsForCameraTarget(limit)

    }
    private fun listenerGetImageOfGallery() {
        binding.buttonGetImage.setOnClickListener {

            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent,
                MainActivity.REQUEST_CODE_GET_IMAGE
            )

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
                        val imageView = ImageView(activity)
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

                    MainActivity.REQUEST_CODE_SEARCHLOCATION -> {
                        idDocumentFirestore = data.getStringExtra("id")!!
                        showLocation()
                    }

                    MainActivity.REQUEST_CODE_GET_IMAGE -> uploadImage(data)

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


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.map_menu_toolbar,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.map_search_item -> {

                startActivityForResult(
                    Intent(activity, SearchLocationActivity::class.java),
                    REQUEST_CODE_SEARCHLOCATION
                )
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun fieldUrlIsFree(fieldUrl: Any?): Boolean =
        (fieldUrl == null || fieldUrl.toString() == "")

    private fun imageContainerIsFull(i: Int): Boolean = (i >= 5)

}

