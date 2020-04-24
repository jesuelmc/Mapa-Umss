package com.erwin.tecnoagenda

/*
¿Qué es un repositorio?
Una clase de repositorio abstrae el acceso a múltiples fuentes de
datos. El repositorio no forma parte de las bibliotecas de componentes de
arquitectura, pero es una práctica recomendada para la separación de código y
la arquitectura. Una clase de repositorio proporciona una API limpia para el acceso a datos
al resto de la aplicación.

¿Por qué usar un repositorio?
Un repositorio gestiona consultas y le
 permite usar múltiples backends. En el ejemplo más común, el Repositorio implementa
 la lógica para decidir si recuperar datos de una red o usar resultados almacenados en caché
 en una base de datos local.
 */


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.erwin.tecnoagenda.Dao.MapLocationDao
import com.erwin.tecnoagenda.Models.MapLocationModel

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

/*
1 El DAO se pasa al constructor del repositorio en lugar de toda la base de datos.
Esto se debe a que solo necesita acceso al DAO, ya que el DAO contiene todos los métodos de
lectura / escritura para la base de datos. No es necesario exponer toda la base de datos al repositorio.

2 La lista de palabras es una propiedad pública. Se inicializa al obtener la LiveDatalista de
palabras de Room; podemos hacer esto debido a cómo definimos el getAlphabetizedWordsmétodo para
 regresar LiveDataen el paso "La clase LiveData". Room ejecuta todas las consultas en un hilo separado.
  Luego observado LiveDatanotificará al observador en el hilo principal cuando los datos hayan cambiado.

3 El suspendmodificador le dice al compilador que esto debe llamarse desde una rutina u
otra función de suspensión


Los repositorios están destinados a mediar entre diferentes fuentes de datos. En este ejemplo simple,
 solo tiene una fuente de datos, por lo que el Repositorio no hace mucho. Vea BasicSample: para una
  implementación más compleja.
 */
