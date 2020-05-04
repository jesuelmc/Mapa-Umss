package com.erwinlaura.agendafcyt


import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import com.erwinlaura.agendafcyt.Dao.MapLocationDao
import com.erwinlaura.agendafcyt.Models.MapLocationModel
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.util.*

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class MapLocationRepository(private val mapLocationDao:MapLocationDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allMapLocation: LiveData<MutableList<MapLocationModel>> = mapLocationDao.getAllLocationsMap()
    val allTypeMapLocations:LiveData<List<MapLocationModel>> =mapLocationDao.getAllTypeMapLocations()

    suspend fun insert(mapLocationModel: MapLocationModel) {
        mapLocationDao.insert(mapLocationModel)
    }
    suspend fun getAllLocationsMapAccordingType(type:String):List<MapLocationModel> {
        return mapLocationDao.getAllLocationsMapAccordingType(type)
    }
    suspend fun geLocation(id:Int):MapLocationModel{
        return mapLocationDao.getLocation(id)
    }
}
