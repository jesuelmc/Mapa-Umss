package com.erwin.tecnoagenda.Maps.SearchLocationActivity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.erwin.tecnoagenda.Models.MapLocationModel
import com.erwin.tecnoagenda.R

class LocationTypeAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<LocationTypeAdapter.LocationMapViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var locations = emptyList<MapLocationModel>() // Cached copy of locations

    inner class LocationMapViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val LocationTypeItemView: TextView = itemView.findViewById(R.id.textViewLocationType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationMapViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_location_type, parent, false)
        return LocationMapViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LocationMapViewHolder, position: Int) {
        val current = locations[position]
        holder.LocationTypeItemView.text = current.type
    }

    internal fun setlocations(locations: List<MapLocationModel>) {
        this.locations = locations
        notifyDataSetChanged()
    }

    override fun getItemCount() = locations.size
}