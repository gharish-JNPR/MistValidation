package com.mist.mistvalidation.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Utils {
    companion object{
        fun getListFromString(listName: String?): ArrayList<String> {
            val list =
                Gson().fromJson<ArrayList<String>>(listName, object : TypeToken<ArrayList<String?>?>() {
                }.type)
            return list
        }
    }
}