package com.example.mindflow_offline_app.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mindflow_offline_app.data.db.Exercise
import com.example.mindflow_offline_app.data.db.ExerciseDao
import com.example.mindflow_offline_app.data.db.MoodDao
import com.example.mindflow_offline_app.data.db.TestResult
import com.example.mindflow_offline_app.data.db.TestResultDao
import com.example.mindflow_offline_app.data.model.Mood
import kotlinx.coroutines.flow.Flow

@Database(entities = [Mood::class, Exercise::class, TestResult::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moodDao(): MoodDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun testResultDao(): TestResultDao
}