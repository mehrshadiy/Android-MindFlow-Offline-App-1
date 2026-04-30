package com.example.mindflow_offline_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindflow_offline_app.data.model.Mood
import com.example.mindflow_offline_app.repository.MoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoodViewModel @Inject constructor(private val repository: MoodRepository) : ViewModel() {
    val moods: StateFlow<List<Mood>> = repository.moods.stateIn(
        viewModelScope, SharingStarted.Lazily, emptyList()
    )

    fun addMood(mood: Mood) {
        viewModelScope.launch { repository.addMood(mood) }
    }
}