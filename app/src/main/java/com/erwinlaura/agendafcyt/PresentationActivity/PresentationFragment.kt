package com.erwinlaura.agendafcyt.PresentationActivity


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.erwinlaura.agendafcyt.R
import com.erwinlaura.agendafcyt.databinding.MapFragmentPresentationBinding

/**
 * A simple [Fragment] subclass.
 */
class PresentationFragment : Fragment() {


    private lateinit var binding:MapFragmentPresentationBinding

    companion object{
        var COMPLETED_PRESENTATION="com.erwinlaura.agendafcyt.completedPresentation"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding=DataBindingUtil.inflate(inflater,R.layout.map_fragment_presentation, container, false)


        return binding.root
    }




}
