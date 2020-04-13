//package com.erwin.tecnoagenda.Fragments
//
//
//import android.graphics.Camera
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.MenuItem
//import android.view.View
//import android.view.ViewGroup
//import androidx.databinding.DataBindingUtil
//
//import com.erwin.tecnoagenda.R
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.MarkerOptions
//import com.erwin.tecnoagenda.databinding.FragmentMapBinding
//import com.google.android.gms.maps.*
//import com.google.android.gms.maps.model.CameraPosition
//import com.google.android.gms.maps.model.LatLngBounds
//
//
///**
// * A simple [Fragment] subclass.
// */
//class MapFragment : Fragment(),OnMapReadyCallback {
//
//
//    lateinit var mapView :MapView
//    lateinit var gMap: GoogleMap
//    lateinit var binding: FragmentMapBinding
//
//    //private val viewModel=ViewModelProvider(this).get(SearchLocationActivityViewModel::class.java)
//
//
//
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        binding= DataBindingUtil.inflate(
//            inflater,
//            R.layout.fragment_map,
//            container,
//            false)
////        (binding.root.findViewById<View>(R.id.search_location_map_activity) as EditText).setOnClickListener {view :View->
////            findNavController().navigate(R.id.action_mapFragment_to_searchLocationMap)
////        }
//
//
//
//
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        mapView=binding.IdMapFragment
//
//        if(mapView!=null){
//            mapView.onCreate(null)
//            mapView.onResume()
//            mapView.getMapAsync(this)
//        }
//
//
//    }
//
//
//    override fun onMapReady(p0: GoogleMap?) {
//        if (p0 != null) {
//            gMap=p0
//        }
//        val campusCentral = LatLng(-17.39356623953956,-66.14553816328916)
//        //gMap.addMarker(MarkerOptions().position(campusCentral).title("Marker in Sydney"))
//
//        val camera: CameraPosition? =CameraPosition.builder().target(campusCentral).zoom(18F).bearing(0F).tilt(90F).build()
//        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera))
//        //gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(campusCentral,18F))
//        gMap.setMaxZoomPreference(18F)
//
//        val limit = LatLngBounds(LatLng(-17.39307480327583,-66.14951160041626), LatLng(-17.39173972730723,-66.14224269812405))
//        gMap.setLatLngBoundsForCameraTarget(limit)
//
//
//
//
//    }
//
//}
//
//
