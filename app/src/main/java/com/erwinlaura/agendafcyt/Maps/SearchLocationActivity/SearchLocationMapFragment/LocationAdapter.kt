package com.erwinlaura.agendafcyt.Maps.SearchLocationActivity.SearchLocationMapFragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.erwinlaura.agendafcyt.R
import com.google.firebase.firestore.DocumentSnapshot

class LocationAdapter internal constructor(
    context: Context, activity: FragmentActivity
) : RecyclerView.Adapter<LocationAdapter.LocationMapViewHolder>() {

    private val activity: FragmentActivity = activity
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var locations: MutableList<DocumentSnapshot> = ArrayList()// Cached copy of locations
    //private var locationsCopy:MutableList<MapLocationModelAux> = ArrayList()


    inner class LocationMapViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            val mPosition: Int = layoutPosition
            val locationID = locations[mPosition].id
            val intent = Intent()
            intent.putExtra("id", locationID)
            activity.setResult(Activity.RESULT_OK, intent)
            activity.finish()
        }

        val LocationTypeItemView: TextView = itemView.findViewById(R.id.textViewLocationType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationMapViewHolder {
        val itemView = inflater.inflate(R.layout.map_recyclerview_location_type, parent, false)
        return LocationMapViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LocationMapViewHolder, position: Int) {
        val current = locations[position]["name"].toString()
        holder.LocationTypeItemView.text = current
    }

    fun filter(text: String, locationsCopy: MutableList<DocumentSnapshot>) {
        locations.clear()
        if (text.isEmpty()) {
            locations.addAll(locationsCopy)
        } else {
            locationsCopy.forEach { item ->
                if (item["name"].toString().toLowerCase().contains(text.toLowerCase())) {
                    locations.add(item)
                }
            }

        }
        notifyDataSetChanged()
    }

    override fun getItemCount() = locations.size
}