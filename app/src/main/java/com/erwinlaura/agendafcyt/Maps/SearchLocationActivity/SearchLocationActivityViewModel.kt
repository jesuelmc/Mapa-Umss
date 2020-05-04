package com.erwinlaura.agendafcyt

import android.app.Application
import androidx.lifecycle.*
import com.erwinlaura.agendafcyt.Models.MapLocationModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchLocationActivityViewModel(application: Application) : AndroidViewModel(application) {



    val repository: MapLocationRepository
    val searchViewText: MutableLiveData<String> = MutableLiveData()

    //firestore
    var dbFirestore= FirebaseFirestore.getInstance()
    val alltypeMapLocations=dbFirestore.collection("Locations").document("Types")
    var locations=dbFirestore.collection("Locations")

    init {
        val mapLocationDao = DataBase.getDatabase(application,viewModelScope).mapLocationDao()
        repository = MapLocationRepository(mapLocationDao)
    }

   fun insert(mapLocation: MapLocationModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(mapLocation)
    }
    suspend fun getAllLocationsMapAccordingType(type:String):List<MapLocationModel>{
        return repository.getAllLocationsMapAccordingType(type)
    }
    fun <Locations,Text> joinLocationsSearchViewText(locations:LiveData<Locations>, searchViewText:LiveData<Text>):LiveData<Pair<Locations,Text>>{
        return MediatorLiveData<Pair<Locations,Text>>().apply {
            var lastLocation:Locations?=null
            var text:Text?=null
            fun update() {
                val localLastLocation = lastLocation
                val localLastText = text

                if (localLastLocation != null && localLastText != null) this.value =
                    Pair(localLastLocation, localLastText)
            }
            addSource(locations){
                lastLocation=it
                update()
            }
            addSource(searchViewText){
                text=it
                update()
            }

            }
        }
    }










/*
Aquí tenemos:

1 Creó una clase llamada WordViewModelque obtiene Applicationcomo parámetro y
se extiende AndroidViewModel.
2 Se agregó una variable de miembro privado para contener
 una referencia al repositorio.
3 Se agregó una LiveDatavariable miembro público para almacenar
en caché la lista de palabras.
4 Creó un initbloque que obtiene una referencia WordDaode
WordRoomDatabase.
5 En el initbloque, construido el WordRepositorybasado en el WordRoomDatabase.
6 En el initbloque, inicializó allWordsLiveData usando el repositorio.
7Creó un insert()método contenedor que llama al insert()método del Repositorio .
 De esta manera, la implementación de insert()se encapsula desde la interfaz de usuario.
 No queremos que insert bloquee el hilo principal, por lo que estamos lanzando una nueva rutina
 y llamando al insert del repositorio, que es una función de suspensión. Como se mencionó,
 los ViewModels tienen un alcance de rutina basado en su ciclo de vida llamado viewModelScope,
 que usamos aquí.




 Advertencia : ¡No guarde una referencia a un contexto que tenga un ciclo de vida más corto que
 su ViewModel! Ejemplos son:

Actividad
Fragmento
View

Mantener una referencia puede causar una pérdida de memoria, por ejemplo,
 ViewModel tiene una referencia a una actividad destruida. Todos estos objetos pueden ser
 destruidos por el sistema operativo y recreados cuando hay un cambio de configuración, y esto
  puede suceder muchas veces durante el ciclo de vida de un ViewModel.

Si necesita el contexto de la aplicación (que tiene un ciclo de vida que AndroidViewModeldura
tanto como la aplicación), use , como se muestra en este codelab.



Importante: losViewModel s no sobreviven al proceso de la aplicación que se
elimina en segundo plano cuando el sistema operativo necesita más recursos. Para los
datos de la interfaz de usuario que necesitan sobrevivir a la muerte del proceso debido a la falta
de recursos, puede usar el módulo Estado guardado para ViewModels . Obtenga más información aquí .

 */



