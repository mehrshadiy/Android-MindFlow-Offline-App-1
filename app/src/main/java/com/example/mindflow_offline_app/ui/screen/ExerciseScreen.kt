package com.example.mindflow_offline_app.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mindflow_offline_app.data.db.Exercise
import com.example.mindflow_offline_app.viewmodel.ExerciseViewModel

@Composable
fun ExerciseScreen(exerciseViewModel: ExerciseViewModel) {
    val exercises by exerciseViewModel.exercises.collectAsState()
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("دَم و بازدَم") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("مدیریت تمرینات", style = MaterialTheme.typography.headlineMedium)
        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") })
        OutlinedTextField(value = type, onValueChange = { type = it }, label = { Text("Type") })
        Button(onClick = {
            val exercise = Exercise(title = title, description = description, type = type)
            exerciseViewModel.addExercise(exercise)
            title = ""
            description = ""
        }) { Text("اضافه کردن تمرین") }
        Spacer(Modifier.height(16.dp))
        exercises.forEach {
            Card(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(it.title, fontWeight = FontWeight.Bold)
                    Text(it.description)
                    Text("Type: ${it.type}")
                }
            }
        }
    }
}