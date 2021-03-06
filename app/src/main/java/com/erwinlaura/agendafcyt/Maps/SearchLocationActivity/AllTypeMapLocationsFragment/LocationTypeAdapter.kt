package com.erwinlaura.agendafcyt.Maps.SearchLocationActivity.AllTypeMapLocationsFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.erwinlaura.agendafcyt.R

class LocationTypeAdapter internal constructor(
    context: Context, private val fragment: Fragment
) : RecyclerView.Adapter<LocationTypeAdapter.LocationMapViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var locations: List<Pair<String, Any>> = emptyList()

    inner class LocationMapViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val mPosition: Int = layoutPosition
            val nav = findNavController(fragment)

            val bundle = bundleOf("tipo" to locations[mPosition].second.toString())

            nav.navigate(R.id.action_allTypeMapLocationsFragment_to_allMapLocationsFragment, bundle)
        }

        val LocationTypeItemView: TextView = itemView.findViewById(R.id.textViewLocationType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationMapViewHolder {
        val itemView = inflater.inflate(R.layout.map_recyclerview_location_type, parent, false)
        return LocationMapViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LocationMapViewHolder, position: Int) {
        val current = locations[position]
        holder.LocationTypeItemView.text = current.second.toString()
    }

    internal fun setlocations(locations: List<Pair<String, Any>>) {
        this.locations = locations
        notifyDataSetChanged()
    }

    override fun getItemCount() = locations.size
}