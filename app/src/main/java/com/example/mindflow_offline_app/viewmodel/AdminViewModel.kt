package com.example.mindflow_offline_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindflow_offline_app.data.db.Exercise
import com.example.mindflow_offline_app.data.model.Mood
import com.example.mindflow_offline_app.repository.ExerciseRepository
import com.example.mindflow_offline_app.repository.MoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val moodRepo: MoodRepository,
    private val exerciseRepo: ExerciseRepository
) : ViewModel() {
    val moods: StateFlow<List<Mood>> = moodRepo.moods.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val exercises: StateFlow<List<Exercise>> = exerciseRepo.exercises.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}