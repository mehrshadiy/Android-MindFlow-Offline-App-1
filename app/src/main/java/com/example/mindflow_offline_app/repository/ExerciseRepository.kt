package com.example.mindflow_offline_app.repository

import com.example.mindflow_offline_app.data.db.Exercise
import com.example.mindflow_offline_app.data.db.ExerciseDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExerciseRepository @Inject constructor(private val dao: ExerciseDao) {
    val exercises: Flow<List<Exercise>> = dao.getAllExercises()
    suspend fun addExercise(exercise: Exercise) = dao.insertExercise(exercise)
    suspend fun updateExercise(exercise: Exercise) = dao.updateExercise(exercise)
    suspend fun deleteExercise(exercise: Exercise) = dao.deleteExercise(exercise)
}