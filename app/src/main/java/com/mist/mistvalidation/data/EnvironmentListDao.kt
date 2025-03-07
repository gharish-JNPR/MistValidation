package com.mist.mistvalidation.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EnvironmentListDao {

    //this might cause to allowing duplicate entries to enter into the database.handles in view model.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(environmentList: EnvironmentList)

    //:envName is a named Parameter here!!
    @Query("Select * from environment_lists where environmentName=:envName")
    suspend fun getList(envName: String):EnvironmentList?
}