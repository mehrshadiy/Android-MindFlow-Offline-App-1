package com.example.mindflow_offline_app.data.db

import androidx.room.PrimaryKey

@androidx.room.Entity(tableName = "exercises")
data class Exercise(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val type: String
)