package com.example.mindflow_offline_app.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mindflow_offline_app.data.model.Mood
import com.example.mindflow_offline_app.viewmodel.MoodViewModel

@Composable
fun MoodRecordingScreen(moodViewModel: MoodViewModel) {
    var selectedMood by remember { mutableStateOf("Normal") }
    var note by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("حال و هواتون را ثبت کنید\n", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(8.dp))
        val moods = listOf("عالی", "خوب", "معمولی", "بد", "خیلی بد")
        moods.forEach { mood ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedMood == mood,
                    onClick = { selectedMood = mood }
                )
                Text(mood)
            }
        }
        OutlinedTextField(
            value = note,
            onValueChange = { note = it },
            label = { Text("یادداشت اختیاری") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        Button(onClick = {
            val newMood = Mood(moodType = selectedMood, note = note, date = System.currentTimeMillis())
            moodViewModel.addMood(newMood)
            note = ""
        }) {
            Text("ذخیره کردن")
        }
    }
}