package com.erwinlaura.agendafcyt.Maps.MapActivity

import android.app.Application
import android.net.Uri
import androidx.lifecycle.*
import com.erwinlaura.agendafcyt.DataBase
import com.erwinlaura.agendafcyt.MapLocationRepository
import com.erwinlaura.agendafcyt.Models.MapLocationModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch

class MapActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MapLocationRepository
    lateinit var location:LiveData<MapLocationModel>

    //cloudstore
    var storageFirebase= FirebaseStorage.getInstance()
    var storageRef=storageFirebase.reference

    //firestore
    var dbFirestore=FirebaseFirestore.getInstance()

    init {
        val mapLocationDao = DataBase.getDatabase(application, viewModelScope).mapLocationDao()
        repository = MapLocationRepository(mapLocationDao)
        //eventShowLocation.value=false
    }

    suspend fun getLocation(id: Int): MapLocationModel{
        return repository.geLocation(id)
    }



}