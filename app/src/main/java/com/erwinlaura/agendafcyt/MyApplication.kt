//package com.erwin.tecnoagenda
//
//import android.app.Application
//import io.realm.Realm
//import io.realm.RealmConfiguration
//
//open class MyApplication : Application() {
//
//    override fun onCreate() {
//        super.onCreate()
//
//        //configuration for Realm
//        Realm.init(this)
//        val config=RealmConfiguration.Builder()
//            .name("sample.realm")
//            .schemaVersion(1)
//            .deleteRealmIfMigrationNeeded()
//            .build()
//        Realm.setDefaultConfiguration(config)
//
//
//    }
//}