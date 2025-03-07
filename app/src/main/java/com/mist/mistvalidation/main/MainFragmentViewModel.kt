package com.mist.mistvalidation.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.mist.mistvalidation.MistValidationApplication.Companion.environmentListDao
import com.mist.mistvalidation.data.EnvironmentList

class MainFragmentViewModel : ViewModel() {
    suspend fun getList(envName: String) : String?{
        return environmentListDao.getList(envName)?.orgList
    }
    suspend fun putList(envName: String, orgList: String){
        val existingEntry = environmentListDao.getList(envName)
        Log.d("DB_DEBUG", "Existing Entry: $existingEntry")
        if (existingEntry == null || existingEntry.orgList != orgList) {
            environmentListDao.insertList(EnvironmentList(envName, orgList))
            Log.d("DB_DEBUG", "Inserted new entry for $envName")
        }
    }
}