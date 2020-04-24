package com.erwin.tecnoagenda.Maps


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.erwin.tecnoagenda.Maps.SearchLocationActivity.LocationsAcordingTypeAdapter
import com.erwin.tecnoagenda.Models.MapLocationModel

import com.erwin.tecnoagenda.R
import com.erwin.tecnoagenda.databinding.FragmentAllLocationsAcordingTypeBinding
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class LocationsAcordingTypeFragment : Fragment() {

    private lateinit var binding: FragmentAllLocationsAcordingTypeBinding
    private lateinit var viewModel: LocationsAcordingTypeViewModel

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

        viewModel= ViewModelProvider(this).get(LocationsAcordingTypeViewModel::class.java)


        lifecycle.coroutineScope.launch {
            val locations=arguments?.getString("tipo")
            val locationsAcordingType: List<MapLocationModel> =viewModel.getLocationsAcordingType(locations!!)
            adapter.setlocations(locationsAcordingType)
            }


        return binding.root
    }


}
