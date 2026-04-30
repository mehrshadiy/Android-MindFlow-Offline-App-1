package com.example.mindflow_offline_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindflow_offline_app.data.db.Exercise
import com.example.mindflow_offline_app.repository.ExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(private val repository: ExerciseRepository) : ViewModel() {
    val exercises: StateFlow<List<Exercise>> = repository.exercises.stateIn(
        viewModelScope, SharingStarted.Lazily, emptyList()
    )

    fun addExercise(exercise: Exercise) = viewModelScope.launch { repository.addExercise(exercise) }
    fun updateExercise(exercise: Exercise) = viewModelScope.launch { repository.updateExercise(exercise) }
    fun deleteExercise(exercise: Exercise) = viewModelScope.launch { repository.deleteExercise(exercise) }
}