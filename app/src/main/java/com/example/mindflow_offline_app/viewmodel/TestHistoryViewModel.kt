package com.example.mindflow_offline_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mindflow_offline_app.data.db.TestResult
import com.example.mindflow_offline_app.repository.TestResultRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestHistoryViewModel @Inject constructor(private val repository: TestResultRepository) : ViewModel() {
    private val _results = MutableStateFlow<List<TestResult>>(emptyList())
    val results: StateFlow<List<TestResult>> get() = _results

    fun loadResults() {
        viewModelScope.launch {
            _results.value = repository.getAllResults()
        }
    }

    fun addResult(score: Int) {
        viewModelScope.launch {
            repository.insert(TestResult(score = score))
            loadResults()
        }
    }
}