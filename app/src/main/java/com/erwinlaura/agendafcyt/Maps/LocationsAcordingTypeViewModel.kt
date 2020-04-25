package com.erwinlaura.agendafcyt.Maps

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.erwinlaura.agendafcyt.DataBase
import com.erwinlaura.agendafcyt.MapLocationRepository
import com.erwinlaura.agendafcyt.Models.MapLocationModel

class LocationsAcordingTypeViewModel (application: Application) : AndroidViewModel(application) {



    val repository: MapLocationRepository
// Using LiveData and caching what getAlphabetizedWords returns has several benefits:
// - We can put an observer on the data (instead of polling for changes) and only update the
//   the UI when the data actually changes.
    init {
        val mapLocationDao = DataBase.getDatabase(application, viewModelScope).mapLocationDao()
        repository = MapLocationRepository(mapLocationDao)
    }

    suspend fun getLocationsAcordingType(type:String):List<MapLocationModel>{
        return repository.getAllLocationsMapAccordingType(type)
    }
}