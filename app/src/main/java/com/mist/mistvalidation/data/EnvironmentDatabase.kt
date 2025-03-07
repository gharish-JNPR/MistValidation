package com.mist.mistvalidation.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EnvironmentList::class],version = 4 )
abstract class EnvironmentDatabase : RoomDatabase(){
    abstract fun getEnvironmentListDao(): EnvironmentListDao

    companion object {
        @Volatile
        private var INSTANCE: EnvironmentDatabase? = null

        fun getDatabase(context: Context): EnvironmentDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    EnvironmentDatabase::class.java,
                    "mist_experience_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}