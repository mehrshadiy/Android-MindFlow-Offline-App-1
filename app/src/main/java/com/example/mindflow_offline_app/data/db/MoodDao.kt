package com.example.mindflow_offline_app.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mindflow_offline_app.data.model.Mood
import kotlinx.coroutines.flow.Flow

@Dao
interface MoodDao {
    @Insert
    suspend fun insertMood(mood: Mood)

    @Query("SELECT * FROM moods ORDER BY date DESC")
    fun getAllMoods(): Flow<List<Mood>>
}