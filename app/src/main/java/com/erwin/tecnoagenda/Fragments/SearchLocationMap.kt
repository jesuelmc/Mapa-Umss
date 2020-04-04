package com.erwin.tecnoagenda.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.erwin.tecnoagenda.R

/**
 * A simple [Fragment] subclass.
 */
class SearchLocationMap : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val root=inflater.inflate(R.layout.fragment_search_location_map, container, false)



        return root
    }


}
