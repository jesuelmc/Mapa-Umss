package com.erwinlaura.agendafcyt.Maps.SearchLocationActivity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.erwinlaura.agendafcyt.Models.MapLocationModel
import com.erwinlaura.agendafcyt.R

class LocationTypeAdapter internal constructor(
    context: Context,fragment: Fragment
) : RecyclerView.Adapter<LocationTypeAdapter.LocationMapViewHolder>() {

    private val fragment:Fragment=fragment
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var locations = emptyList<MapLocationModel>() // Cached copy of locations

    inner class LocationMapViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val mPosition:Int=layoutPosition
            val type: MapLocationModel = locations[mPosition]

            val nav=findNavController(fragment)

            var bundle= bundleOf("tipo" to type.type)

            nav.navigate(R.id.action_allTypeMapLocationsFragment_to_allMapLocationsFragment,bundle)
        }

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