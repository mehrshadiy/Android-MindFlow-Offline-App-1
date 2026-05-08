package com.example.mindflow_offline_app.data.model

data class Question(
    val id: Int,
    val text: String,
    val options: List<String>,
    val correctAnswerIndex: Int? = null,
    val isPositive: Boolean = true  // true = سوال مثبت، false = سوال منفی
)
