package com.erwin.tecnoagenda.Maps.MapActivity

import android.app.Application
import androidx.lifecycle.*
import com.erwin.tecnoagenda.DataBase
import com.erwin.tecnoagenda.MapLocationRepository
import com.erwin.tecnoagenda.Models.MapLocationModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

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