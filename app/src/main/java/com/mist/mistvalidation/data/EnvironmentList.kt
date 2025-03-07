package com.mist.mistvalidation.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "environment_lists")
data class EnvironmentList (
    @PrimaryKey val environmentName: String ,
    val orgList: String
)