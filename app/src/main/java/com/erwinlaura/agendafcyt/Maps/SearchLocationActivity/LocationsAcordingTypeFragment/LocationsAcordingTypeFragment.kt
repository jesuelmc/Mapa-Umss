package com.erwinlaura.agendafcyt.Maps.SearchLocationActivity.LocationsAcordingTypeFragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager

import com.erwinlaura.agendafcyt.R
import com.erwinlaura.agendafcyt.databinding.MapFragmentAllLocationsAcordingTypeBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source

/**
 * A simple [Fragment] subclass.
 */
class LocationsAcordingTypeFragment : Fragment() {

    private lateinit var binding: MapFragmentAllLocationsAcordingTypeBinding

    var dbFirestore = FirebaseFirestore.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.map_fragment_all_locations_acording_type,
            container,
            false
        )


        val recyclerView = binding.RecyclerViewAllLocationsAcordingType
        val adapter = context?.let {
            activity?.let { it1 ->
                LocationsAcordingTypeAdapter(
                    it,
                    it1
                )
            }
        }!!
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        val locationBundle = arguments?.getString("tipo")
        dbFirestore.collection("Locations")
            .whereEqualTo("type", locationBundle)
            .get(Source.CACHE)
            .addOnSuccessListener {
                val locationsAcordingtype = it.documents.toList()
                adapter.setlocations(locationsAcordingtype)
            }




        return binding.root
    }


}
