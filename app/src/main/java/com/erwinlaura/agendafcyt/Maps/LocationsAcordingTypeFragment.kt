package com.erwinlaura.agendafcyt.Maps


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.erwinlaura.agendafcyt.Maps.SearchLocationActivity.LocationsAcordingTypeAdapter
import com.erwinlaura.agendafcyt.Models.MapLocationModel

import com.erwinlaura.agendafcyt.R
import com.erwinlaura.agendafcyt.databinding.FragmentAllLocationsAcordingTypeBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class LocationsAcordingTypeFragment : Fragment() {

    private lateinit var binding: FragmentAllLocationsAcordingTypeBinding

    var dbFirestore= FirebaseFirestore.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_all_locations_acording_type, container, false)
        //binding.textViewMapLocation.text = arguments?.getString("tipo")

        val recyclerView =binding.RecyclerViewAllLocationsAcordingType
        val adapter = context?.let { activity?.let { it1 -> LocationsAcordingTypeAdapter(it, it1) } }!!
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

//        lifecycle.coroutineScope.launch {
//            val locations=arguments?.getString("tipo")
//            val locationsAcordingType: List<MapLocationModel> =viewModel.getLocationsAcordingType(locations!!)
//            adapter.setlocations(locationsAcordingType)
//            }

        val locationBundle=arguments?.getString("tipo")
        dbFirestore.collection("Locations")
            .whereEqualTo("type",locationBundle)
            .get(Source.CACHE)
            .addOnSuccessListener {
                val locationsAcordingtype= it.documents.toList()
                adapter.setlocations(locationsAcordingtype)
        }


        return binding.root
    }


}
