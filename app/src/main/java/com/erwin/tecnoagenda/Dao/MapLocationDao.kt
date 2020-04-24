package com.erwin.tecnoagenda.Dao


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.erwin.tecnoagenda.Models.MapLocationModel

//es una clase para Dao
@Dao
interface MapLocationDao {
//
//    @Query("SELECT * from map_location ORDER BY word ASC")  //LiveData =Cuando los datos cambian, generalmente desea realizar alguna acción, como mostrar los datos actualizados en la interfaz de usuario. Esto significa que debe observar los datos para que, cuando cambien, pueda reaccionar.mediante una Observer
//    fun getAlphabetizedWords(): LiveData<List<Word>>   //fun getAlphabetizedWords(): List<Word>: Un método para obtener todas las palabras y hacer que devuelva un Listde Words
//
    @Insert(onConflict = OnConflictStrategy.IGNORE)  //los insert no debene poner consutas sql, asi como delete oupdate
    suspend fun insert(mapLocation: MapLocationModel)   //suspend es para insertar datos ,onConflict = OnConflictStrategy.IGNORE: La estrategia seleccionada en conflicto ignora una nueva palabra si es exactamente la misma que ya está en la lista. Para saber más sobre las estrategias de conflicto disponibles, consulte la documentación .
//
    @Query("DELETE FROM map_location")
    suspend fun deleteAll()

    @Query("SELECT * FROM map_location WHERE type=:type")
    suspend fun getAllLocationsMapAccordingType(type:String):List<MapLocationModel>

    @Query("SELECT * FROM map_location GROUP BY type")
    fun getAllTypeMapLocations():LiveData<List<MapLocationModel>>

    @Query("SELECT * FROM map_location")
    fun getAllLocationsMap():LiveData<MutableList<MapLocationModel>>

    @Query("SELECT * FROM map_location WHERE id=:id")
    suspend fun getLocation(id:Int):MapLocationModel

}
