package com.example.mindflow_offline_app.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mindflow_offline_app.data.model.Question
import com.example.mindflow_offline_app.viewmodel.AdminViewModel
import com.example.mindflow_offline_app.viewmodel.MentalHealthTestViewModel

@Composable
fun AdminScreen(
    adminViewModel: AdminViewModel,
    testViewModel: MentalHealthTestViewModel = hiltViewModel(),
    correctPin: String = "1234"
) {
    var pin by remember { mutableStateOf("") }
    var authenticated by remember { mutableStateOf(false) }

    if (!authenticated) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("رمز مدیریت را وارد کنید: (پیشفرض: 1234)", style = MaterialTheme.typography.headlineMedium)
            OutlinedTextField(
                value = pin,
                onValueChange = { pin = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { if (pin == correctPin) authenticated = true }) {
                Text("ورود")
            }
        }
    } else {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
        ) {
            Text("پنل مدیریت", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(16.dp))
            Text("تمرینات", style = MaterialTheme.typography.titleMedium)
            val exercises by adminViewModel.exercises.collectAsState()
            exercises.forEach { ex ->
                Card(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(ex.title, fontWeight = FontWeight.Bold)
                        Text(ex.description)
                        Text("نوع تمرین: ${ex.type}")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("اضافه کردن سوال سلامت روان", style = MaterialTheme.typography.titleMedium)

            var questionText by remember { mutableStateOf("") }
            var options by remember { mutableStateOf(listOf("", "", "", "")) }

            OutlinedTextField(
                value = questionText,
                onValueChange = { questionText = it },
                label = { Text("متن سوال") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            options.forEachIndexed { index, option ->
                OutlinedTextField(
                    value = option,
                    onValueChange = { newValue ->
                        options = options.toMutableList().also { it[index] = newValue }
                    },
                    label = { Text("گزینه ${index + 1}") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            Button(
                onClick = {
                    if (questionText.isNotBlank() && options.all { it.isNotBlank() }) {
                        val newQuestion = Question(
                            id = (testViewModel.questions.value.maxOfOrNull { it.id } ?: 0) + 1,
                            text = questionText,
                            options = options
                        )
                        testViewModel.addQuestion(newQuestion)
                        questionText = ""
                        options = listOf("", "", "", "")
                    }
                },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("اضافه کردن سوال")
            }
        }
    }
}