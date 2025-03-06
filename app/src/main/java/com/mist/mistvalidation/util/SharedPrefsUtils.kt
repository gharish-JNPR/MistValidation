package com.mist.mistvalidation.util

import android.content.Context
import android.content.SharedPreferences

class SharedPrefsUtils {
    companion object{
        lateinit var preferences : SharedPreferences
        private val SHARED_PREF_NAME: String = "Mist-Experience"
        private val MODE: Int = Context.MODE_PRIVATE
        fun init(context: Context?) {
            if (context != null) {
                preferences = context.getSharedPreferences(
                    SHARED_PREF_NAME,
                    MODE
                )
            }
        }
        fun getPreferences(context: Context?): SharedPreferences {
            return preferences
        }
        fun readString(context: Context?, key: String?): String? {
            return getPreferences(context).getString(key, "")
        }
        fun writeString(context: Context?, key: String?, value: String?) {
            SharedPrefsUtils.getEditor(context).putString(key, value).apply()
        }
        fun getEditor(context: Context?): SharedPreferences.Editor {
            return getPreferences(context).edit()
        }
    }

}