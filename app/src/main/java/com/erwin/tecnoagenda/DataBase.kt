package com.erwin.tecnoagenda

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.erwin.tecnoagenda.Dao.MapLocationDao
import com.erwin.tecnoagenda.Models.MapLocationModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/*
Anote la clase para que sea una base de datos de Room
@Databasey use los parámetros de anotación para declarar las entidades que
pertenecen a la base de datos y establecer el número de versión. Cada entidad corresponde
a una tabla que se creará en la base de datos. Las migraciones de bases de datos están más
allá del alcance de este codelab, por lo que establecemos exportSchemafalso aquí para evitar
una advertencia de compilación. En una aplicación real, debe considerar configurar un directorio
para que Room use para exportar el esquema, de modo que pueda verificar el esquema actual en su
sistema de control de versiones.

 */


@Database(entities = [MapLocationModel::class], version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase() {

    abstract fun mapLocationDao(): MapLocationDao

    private class DatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var mapLocationDao = database.mapLocationDao()

                    // Delete all content here.
                    mapLocationDao.deleteAll()

                    // Add sample words.
                    var mapLocation = MapLocationModel("617","aula","-17.393000064167797","-66.14484943466293","es")
                    mapLocationDao.insert(mapLocation)

                    mapLocation = MapLocationModel("692F","aula","-17.394670944064305","-66.14484514375916","ss")
                    mapLocationDao.insert(mapLocation)

                    mapLocation = MapLocationModel("biblioteca fctyt","edificios","-17.392672438916257","-66.14552740760705","ff")
                    mapLocationDao.insert(mapLocation)
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: DataBase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): DataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBase::class.java,
                    "agenda_database"
                )
                    .addCallback(DatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}