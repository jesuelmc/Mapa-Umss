package com.erwinlaura.agendafcyt.Maps.MapActivity

import android.app.Application
import androidx.lifecycle.*
import com.erwinlaura.agendafcyt.DataBase
import com.erwinlaura.agendafcyt.MapLocationRepository
import com.erwinlaura.agendafcyt.Models.MapLocationModel

class MapActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MapLocationRepository

    lateinit var location:LiveData<MapLocationModel>



    init {
        val mapLocationDao = DataBase.getDatabase(application, viewModelScope).mapLocationDao()
        repository = MapLocationRepository(mapLocationDao)
        //eventShowLocation.value=false

    }


    suspend fun getLocation(id: Int): MapLocationModel{
        return repository.geLocation(id)
    }

    fun setLocation(id:Int){
        location= liveData {
            val data=repository.geLocation(id)
            emit(data)
        }
    }

}