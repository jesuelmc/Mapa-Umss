package com.erwin.tecnoagenda.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "map_location")
class MapLocationModel(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "latitude") val latitude: String,
    @ColumnInfo(name = "longitude") val longitude: String,
    @ColumnInfo(name="description") val description:String,
    @PrimaryKey(autoGenerate=true)
    @ColumnInfo(name="id") val idAutoGenerate: Int=0
    )