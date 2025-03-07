package com.mist.mistvalidation

import android.app.Application
import com.mist.mistvalidation.data.EnvironmentDatabase
import com.mist.mistvalidation.data.EnvironmentListDao

class MistValidationApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        database = EnvironmentDatabase.getDatabase(this)
        environmentListDao = database.getEnvironmentListDao()
    }
    companion object{
        lateinit var database: EnvironmentDatabase
        lateinit var environmentListDao: EnvironmentListDao
    }
}