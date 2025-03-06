package com.mist.mistvalidation.main

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.mist.mistvalidation.R
import com.mist.mistvalidation.util.SharedPrefsUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SharedPrefsUtils.init(this)
        loadMainFragment()
    }

    private fun loadMainFragment() {
        supportFragmentManager.commit {
            replace(R.id.frame_main, MainFragment()) // Replace with MainFragment
        }
    }
}