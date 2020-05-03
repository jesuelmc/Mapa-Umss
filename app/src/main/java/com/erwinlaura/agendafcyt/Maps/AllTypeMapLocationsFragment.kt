package com.erwinlaura.agendafcyt.Maps


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.erwinlaura.agendafcyt.Maps.SearchLocationActivity.LocationTypeAdapter

import com.erwinlaura.agendafcyt.R
import com.erwinlaura.agendafcyt.SearchLocationActivityViewModel
import com.erwinlaura.agendafcyt.databinding.FragmentAllTypeMapLocationsBinding
import com.google.firebase.firestore.Source

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
//        searchLocationViewModel.alltypeMapLocations.observe(viewLifecycleOwner, Observer {type->
//            //type.let { adapter?.setlocations(it) }
//
//        })


//        searchLocationViewModel.alltypeMapLocations.get(Source.CACHE).addOnSuccessListener {
//            if(it.exists()) adapter?.setlocations(it.data!!.toList())
//        }
//        searchLocationViewModel.alltypeMapLocations.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
////            if(documentSnapshot!=null && documentSnapshot.metadata.hasPendingWrites()){
////                //local
////            }
////            else{
////                //server
////            }
//            if (documentSnapshot != null) adapter?.setlocations(documentSnapshot.data!!.toList())
//
//        }

        searchLocationViewModel.alltypeMapLocations.get(Source.CACHE).addOnSuccessListener {

             adapter?.setlocations(it.data!!.toList())
        }


        return binding.root
    }

}
