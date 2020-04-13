package com.erwin.tecnoagenda.Maps


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.erwin.tecnoagenda.Maps.SearchLocationActivity.LocationTypeAdapter

import com.erwin.tecnoagenda.R
import com.erwin.tecnoagenda.SearchLocationActivityViewModel
import com.erwin.tecnoagenda.databinding.FragmentAllLocationsMapBinding

/**
 * A simple [Fragment] subclass.
 */
class AllLocationsMapFragment : Fragment() {

    lateinit var binding: FragmentAllLocationsMapBinding
    private lateinit var searchLocationViewModel:SearchLocationActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_all_locations_map, container, false)

        val recyclerView =binding.RecyclerViewAllMapLocations
        val adapter = context?.let { LocationTypeAdapter(it) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        searchLocationViewModel=ViewModelProvider(this).get(SearchLocationActivityViewModel::class.java)
        searchLocationViewModel.allLocationMap.observe(viewLifecycleOwner, Observer {locations->
            locations.let { adapter?.setlocations(it) }

        })

        return binding.root
    }


}
