package com.erwinlaura.agendafcyt.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.erwinlaura.agendafcyt.Maps.SearchLocationActivity.LocationAdapter

import com.erwinlaura.agendafcyt.R
import com.erwinlaura.agendafcyt.SearchLocationActivityViewModel
import com.erwinlaura.agendafcyt.databinding.FragmentSearchLocationMapBinding
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Source

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

//        viewModel.joinLocationsSearchviewtext.observe(viewLifecycleOwner, Observer {
//            adapter.filter(it.second,it.first)
//        })
        viewModel.locations.get(Source.CACHE).addOnSuccessListener {
            val locations = it.documents.toMutableList()
            viewModel.searchViewText.observe(viewLifecycleOwner, Observer {newText->

                adapter.filter(newText,locations)

            })

        }


        return binding.root
    }

}
