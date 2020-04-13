package com.erwin.tecnoagenda
/*
¿Qué es un modelo de vista?
La ViewModelfunción de este es proporcionar datos a la interfaz de usuario y sobrevivir
a los cambios de configuración. A ViewModelactúa como un centro de comunicación entre el repositorio
y la interfaz de usuario. También puede usar a ViewModelpara compartir datos entre fragmentos. ViewModel
 es parte de la biblioteca del ciclo de vida . arquitectura: https://developer.android.com/topic/libraries/architecture/viewmodel.html


 ¿Por qué usar un ViewModel?
A ViewModelguarda los datos de la interfaz de usuario de su aplicación de una manera
consciente del ciclo de vida que sobrevive a los cambios de configuración. La separación de los datos
de la interfaz de usuario de tu aplicación de sus Activityy Fragmentclases le permite seguir mejor el
 principio de la responsabilidad individual: Sus actividades y fragmentos son responsables de elaborar
 los datos a la pantalla, mientras que su ViewModelpuede hacerse cargo de la explotación y el
 procesamiento de todos los datos necesarios para la interfaz de usuario.

En el ViewModel, use LiveDatapara datos modificables que la UI usará o mostrará. Usar LiveDatatiene
 varios beneficios:
1 Puede colocar un observador en los datos (en lugar de sondear los cambios) y solo actualizar
la IU cuando los datos realmente cambian.
2 El repositorio y la interfaz de usuario están completamente separados por ViewModel.
3 No hay llamadas a la base de datos desde ViewModel(
todo esto se maneja en el Repositorio), lo que hace que el código sea más verificable.




viewModelScope
En Kotlin, todas las corutinas corren dentro de a CoroutineScope.
Un alcance controla la vida útil de las corutinas a través de su trabajo. Cuando cancela el
 trabajo de un ámbito, cancela todas las rutinas iniciadas en ese ámbito.
La lifecycle-viewmodel-ktxbiblioteca AndroidX agrega una viewModelScopefunción de extensión de
la ViewModelclase, que le permite trabajar con ámbitos.

Para obtener más información sobre cómo trabajar con corutinas en ViewModel,
consulte el Paso 5 de Uso de Kotlin Coroutines en su codelab de la aplicación de Android o
Easy Coroutines en Android: viewModelScope blogpost .:https://medium.com/androiddevelopers/easy-coroutines-in-android-viewmodelscope-25bffb605471
 */



import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.erwin.tecnoagenda.Models.MapLocationModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchLocationActivityViewModel(application: Application) : AndroidViewModel(application) {



    val repository: MapLocationRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allLocationMap: LiveData<List<MapLocationModel>>

    init {
        val mapLocationDao = DataBase.getDatabase(application,viewModelScope).mapLocationDao()
        repository = MapLocationRepository(mapLocationDao)
        allLocationMap = repository.allMapLocation
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
   fun insert(mapLocation: MapLocationModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(mapLocation)
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



