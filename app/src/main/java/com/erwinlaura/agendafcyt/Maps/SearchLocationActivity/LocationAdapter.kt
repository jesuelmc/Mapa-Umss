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

class LocationAdapter internal constructor(
    context: Context, activity:FragmentActivity
) : RecyclerView.Adapter<LocationAdapter.LocationMapViewHolder>() {

    private val activity:FragmentActivity=activity
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var locations:MutableList<MapLocationModel> = ArrayList()// Cached copy of locations
    //private var locationsCopy:MutableList<MapLocationModelAux> = ArrayList()


    inner class LocationMapViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {

            val mPosition:Int=layoutPosition
            val location: MapLocationModel = locations[mPosition]

            val intent =Intent()
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

    fun filter(text:String, locationsCopy:MutableList<MapLocationModel>){
        locations.clear()
        if(text.isEmpty()){
            locations.addAll(locationsCopy)
        }
        else{
            locationsCopy.forEach {item->
                if(item.name.toLowerCase().contains(text.toLowerCase())){
                    locations.add(item)
                }
            }

        }
        notifyDataSetChanged()
    }

    override fun getItemCount() = locations.size
}