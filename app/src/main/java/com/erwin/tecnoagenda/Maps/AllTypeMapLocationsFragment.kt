package com.erwin.tecnoagenda.Maps


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erwin.tecnoagenda.Maps.SearchLocationActivity.LocationTypeAdapter

import com.erwin.tecnoagenda.R
import com.erwin.tecnoagenda.SearchLocationActivityViewModel
import com.erwin.tecnoagenda.databinding.FragmentAllTypeMapLocationsBinding

/**
 * A simple [Fragment] subclass.
 */
class AllTypeMapLocationsFragment : Fragment() {

    lateinit var binding: FragmentAllTypeMapLocationsBinding
    private lateinit var searchLocationViewModel:SearchLocationActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_all_type_map_locations, container, false)

        val recyclerView =binding.RecyclerViewAllTypeMapLocations
        val adapter = context?.let { LocationTypeAdapter(it,this) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        searchLocationViewModel=ViewModelProvider(this).get(SearchLocationActivityViewModel::class.java)
        searchLocationViewModel.alltypeMapLocations.observe(viewLifecycleOwner, Observer {type->
            type.let { adapter?.setlocations(it) }
        })

        return binding.root
    }

}
