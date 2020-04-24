package com.erwin.tecnoagenda.Maps.SearchLocationActivity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.erwin.tecnoagenda.Models.MapLocationModel
import com.erwin.tecnoagenda.R

class LocationsAcordingTypeAdapter internal constructor(
    context: Context,activity: FragmentActivity
) : RecyclerView.Adapter<LocationsAcordingTypeAdapter.LocationMapViewHolder>() {

    private val activity:FragmentActivity=activity
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var locations = emptyList<MapLocationModel>() // Cached copy of locations

    inner class LocationMapViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val mPosition:Int=layoutPosition
            val location: MapLocationModel = locations[mPosition]

            val intent=Intent()
            intent.putExtra("id",(location.idAutoGenerate).toString())
            activity.setResult(Activity.RESULT_OK,intent)
            activity.finish()
        }

        val LocationTypeItemView: TextView = itemView.findViewById(R.id.textViewLocationType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationMapViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_location_type, parent, false)
        return LocationMapViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LocationMapViewHolder, position: Int) {
        val current = locations[position]
        holder.LocationTypeItemView.text = current.name
    }

    internal fun setlocations(locations: List<MapLocationModel>) {
        this.locations = locations
        notifyDataSetChanged()
    }

    override fun getItemCount() = locations.size
}