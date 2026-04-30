package com.example.mindflow_offline_app.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mindflow_offline_app.viewmodel.MentalHealthTestViewModel
import com.example.mindflow_offline_app.viewmodel.TestHistoryViewModel

@Composable
fun MentalHealthTestScreen(
    testViewModel: MentalHealthTestViewModel = hiltViewModel(),
    testHistoryViewModel: TestHistoryViewModel = hiltViewModel(),
    onTestComplete: (score: Int) -> Unit
) {
    val currentIndex by testViewModel.currentQuestionIndex.collectAsState()
    val selectedOptions by testViewModel.selectedOptions.collectAsState()

    val question = testViewModel.getQuestion(currentIndex)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "سوال ${currentIndex + 1} of ${testViewModel.totalQuestions}",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = question.text, style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

        question.options.forEachIndexed { index, option ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = selectedOptions[question.id] == index,
                        onClick = { testViewModel.selectOption(question.id, index) }
                    )
                    .padding(8.dp)
            ) {
                RadioButton(
                    selected = selectedOptions[question.id] == index,
                    onClick = { testViewModel.selectOption(question.id, index) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(option)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (currentIndex > 0) {
                Button(onClick = { testViewModel.previousQuestion() }) {
                    Text("سوال قبل")
                }
            }

            if (currentIndex < testViewModel.totalQuestions - 1) {
                Button(onClick = { testViewModel.nextQuestion() }) {
                    Text("سوال بعد")
                }
            } else {
                Button(
                    onClick = {
                        val score = selectedOptions.values.sum()

                        testHistoryViewModel.addResult(score)
                        onTestComplete(score)
                    }
                ) {
                    Text("ثبت")
                }
            }
        }
    }
}