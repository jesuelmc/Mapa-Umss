package com.erwinlaura.agendafcyt.Maps.SearchLocationActivity.AllTypeMapLocationsFragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.erwinlaura.agendafcyt.R
import com.erwinlaura.agendafcyt.SearchLocationActivityViewModel
import com.erwinlaura.agendafcyt.databinding.MapFragmentAllTypeMapLocationsBinding
import com.google.firebase.firestore.Source

/**
 * A simple [Fragment] subclass.
 */
class AllTypeMapLocationsFragment : Fragment() {

    lateinit var binding: MapFragmentAllTypeMapLocationsBinding
    private lateinit var searchLocationViewModel: SearchLocationActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.map_fragment_all_type_map_locations,
            container,
            false
        )


        val recyclerView = binding.RecyclerViewAllTypeMapLocations
        val adapter = context?.let {
            LocationTypeAdapter(it, this)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        searchLocationViewModel =
            ViewModelProvider(this).get(SearchLocationActivityViewModel::class.java)


        searchLocationViewModel.alltypeMapLocations.get(Source.CACHE).addOnSuccessListener {

            adapter?.setlocations(it.data!!.toList())
        }


        return binding.root
    }
}
