package com.erwinlaura.agendafcyt.Maps.SearchLocationActivity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.erwinlaura.agendafcyt.Models.MapLocationModel
import com.erwinlaura.agendafcyt.R
import com.google.firebase.firestore.DocumentSnapshot

class LocationsAcordingTypeAdapter internal constructor(
    context: Context,activity: FragmentActivity
) : RecyclerView.Adapter<LocationsAcordingTypeAdapter.LocationMapViewHolder>() {

    private val activity:FragmentActivity=activity
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var locations = emptyList<DocumentSnapshot>() // Cached copy of locations

    inner class LocationMapViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val mPosition:Int=layoutPosition
            val locationClickID = locations[mPosition].id

            val intent=Intent()
            intent.putExtra("id",locationClickID)
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
        val current = locations[position].data?.get("name")
        holder.LocationTypeItemView.text = current.toString()
    }

    internal fun setlocations(locations: List<DocumentSnapshot>) {
        this.locations = locations
        notifyDataSetChanged()
    }

    override fun getItemCount() = locations.size
}