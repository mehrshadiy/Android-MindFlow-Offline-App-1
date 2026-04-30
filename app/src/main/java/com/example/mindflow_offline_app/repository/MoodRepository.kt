package com.example.mindflow_offline_app.repository

import com.example.mindflow_offline_app.data.db.MoodDao
import com.example.mindflow_offline_app.data.model.Mood
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoodRepository @Inject constructor(private val dao: MoodDao) {
    val moods: Flow<List<Mood>> = dao.getAllMoods()
    suspend fun addMood(mood: Mood) = dao.insertMood(mood)
}