package com.erwinlaura.agendafcyt.Maps.SearchLocationActivity.SearchLocationMapFragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.erwinlaura.agendafcyt.R
import com.erwinlaura.agendafcyt.SearchLocationActivityViewModel
import com.erwinlaura.agendafcyt.databinding.MapFragmentSearchLocationMapBinding
import com.google.firebase.firestore.Source

/**
 * A simple [Fragment] subclass.
 */
class SearchLocationMapFragment : Fragment() {

    private lateinit var binding: MapFragmentSearchLocationMapBinding
    private lateinit var viewModel: SearchLocationActivityViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.map_fragment_search_location_map,
            container,
            false
        )


        val recyclerView = binding.RecyclerViewAllMapLocations
        val adapter = context?.let {
            activity?.let { it1 ->
                LocationAdapter(
                    it,
                    it1
                )
            }
        }!!
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel =
            activity?.let { ViewModelProvider(it).get(SearchLocationActivityViewModel::class.java) }!!

        viewModel.locations.get(Source.CACHE).addOnSuccessListener {
            val locations = it.documents.toMutableList()
            viewModel.searchViewText.observe(viewLifecycleOwner, Observer { newText ->

                adapter.filter(newText, locations)

            })

        }
        return binding.root
    }

}
