package com.erwinlaura.agendafcyt.Presentation


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.preference.PreferenceManager

import com.erwinlaura.agendafcyt.R
import com.erwinlaura.agendafcyt.databinding.FragmentPresentationBinding

/**
 * A simple [Fragment] subclass.
 */
class PresentationFragment : Fragment() {


    private lateinit var binding:FragmentPresentationBinding

    companion object{
        var COMPLETED_PRESENTATION="com.erwinlaura.agendafcyt.completedPresentation"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_presentation, container, false)


        return binding.root
    }




}
