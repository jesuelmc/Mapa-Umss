package com.erwinlaura.agendafcyt.Dao


import androidx.lifecycle.LiveData
import androidx.room.*
import com.erwinlaura.agendafcyt.Models.MapLocationModel

@Dao
interface MapLocationDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(mapLocation: MapLocationModel)

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
