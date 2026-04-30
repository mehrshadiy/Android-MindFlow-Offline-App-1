package com.example.mindflow_offline_app.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mindflow_offline_app.data.model.Question
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MentalHealthTestViewModel @Inject constructor() : ViewModel() {

    val sampleQuestions = listOf(
        Question(
            id = 1,
            text = "من بیشتر اوقات احساس شادی می\u200Cکنم.",
            options = listOf("هرگز", "گاهی", "اغلب", "همیشه")
        ),
        Question(
            id = 2,
            text = "من احساس اضطراب یا نگرانی دارم.",
            options = listOf("هرگز", "گاهی", "اغلب", "همیشه")
        ),
        Question(
            id = 3,
            text = "من اختلال خواب دارم.",
            options = listOf("هرگز", "گاهی", "اغلب", "همیشه")
        ),
        Question(
            id = 4,
            text = "من از فعالیت های روزانه ام لذت می\u200Cبرم.",
            options = listOf("هرگز", "گاهی", "اغلب", "همیشه")
        ),
        Question(
            id = 5,
            text = "من نسبت به آینده احساس ناامیدی می\u200Cکنم.",
            options = listOf("هرگز", "گاهی", "اغلب", "همیشه")
        ),
        Question(
            id = 6,
            text = "احساس آرامش برایم دشوار است.",
            options = listOf("هرگز", "گاهی", "اغلب", "همیشه")
        ),
        Question(
            id = 7,
            text = "من نگاه مثبتی به زندگی دارم.",
            options = listOf("هرگز", "گاهی", "اغلب", "همیشه")
        )
    )

    private val _questions = MutableStateFlow(sampleQuestions)
    val questions: StateFlow<List<Question>> get() = _questions

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> get() = _currentQuestionIndex

    private val _selectedOptions = MutableStateFlow<Map<Int, Int>>(emptyMap())
    val selectedOptions: StateFlow<Map<Int, Int>> get() = _selectedOptions

    val totalQuestions: Int get() = _questions.value.size

    fun getQuestion(index: Int): Question = _questions.value.getOrElse(index) {
        sampleQuestions.first()
    }

    fun selectOption(questionId: Int, optionIndex: Int) {
        val currentSelection = _selectedOptions.value.toMutableMap()
        currentSelection[questionId] = optionIndex
        _selectedOptions.value = currentSelection
    }


    fun nextQuestion() {
        if (_currentQuestionIndex.value < _questions.value.size - 1) {
            _currentQuestionIndex.value += 1
        }
    }

    fun previousQuestion() {
        if (_currentQuestionIndex.value > 0) {
            _currentQuestionIndex.value -= 1
        }
    }

    fun addQuestion(question: Question) {
        _questions.value = _questions.value + question
    }
}