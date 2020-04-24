package com.erwin.tecnoagenda.Fragments


import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.erwin.tecnoagenda.Maps.SearchLocationActivity.LocationAdapter
import com.erwin.tecnoagenda.Models.MapLocationModel

import com.erwin.tecnoagenda.R
import com.erwin.tecnoagenda.SearchLocationActivityViewModel
import com.erwin.tecnoagenda.databinding.FragmentSearchLocationMapBinding

/**
 * A simple [Fragment] subclass.
 */
class SearchLocationMap : Fragment() {

    private lateinit var binding: FragmentSearchLocationMapBinding
    private lateinit var viewModel: SearchLocationActivityViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_search_location_map, container, false)


        val recyclerView =binding.RecyclerViewAllMapLocations
        val adapter = context?.let { activity?.let { it1 -> LocationAdapter(it, it1) } }!!
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel= activity?.let { ViewModelProvider(it).get(SearchLocationActivityViewModel::class.java) }!!

//        viewModel.allLocationMap.observe(viewLifecycleOwner, Observer {locations->
//            adapter.setlocations(locations)
//        })
//
//        viewModel.searchViewText.observe(viewLifecycleOwner, Observer {
//            adapter.filter(it)
//        })

        viewModel.joinLocationsSearchviewtext.observe(viewLifecycleOwner, Observer {
            adapter.filter(it.second,it.first)
        })
        return binding.root
    }

}
