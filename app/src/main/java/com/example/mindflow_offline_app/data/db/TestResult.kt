package com.example.mindflow_offline_app.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "test_result")
data class TestResult(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Long = System.currentTimeMillis(),
    val score: Int
)