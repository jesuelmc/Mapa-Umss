package com.erwinlaura.agendafcyt

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.erwinlaura.agendafcyt.Dao.MapLocationDao
import com.erwinlaura.agendafcyt.Models.MapLocationModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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

                    mapLocationDao.deleteAll()

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