package com.srcaterersnasik

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.srcaterersnasik.repo.SRCaterersDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SRCatererApp: Application() {
    lateinit var appDatabase:SRCaterersDatabase
    override fun onCreate() {
        super.onCreate()
        appDatabase = Room.databaseBuilder(applicationContext,SRCaterersDatabase::class.java,"SRCaterersDatabase.db")
            .build()

    }
}