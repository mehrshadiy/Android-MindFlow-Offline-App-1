package com.example.mindflow_offline_app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "moods")
data class Mood(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val moodType: String,
    val note: String?,
    val date: Long
)