package com.example.mindflow_offline_app.ui.screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mindflow_offline_app.viewmodel.MoodViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DashboardScreen(
    moodViewModel: MoodViewModel,
    navToAdmin: () -> Unit,
    navToTest: () -> Unit,
    navToTestHistory: () -> Unit,
    navToMoodRecording: () -> Unit,
    navToExercise: () -> Unit,
) {
    val moods by moodViewModel.moods.collectAsState()
    val tips = listOf(
        "برای تجدید قوای ذهنی و جسمانی، کمی پیاده\u200Cروی کنید.",
        "به مدت ۵ دقیقه تنفس عمیق را تمرین کنید.",
        "سه چیزی را که بابت آنها سپاسگزار هستید را بنویسید.",
        "برای کاهش استرس به موسیقی آرامش\u200Cبخش گوش دهید.",
        "نوشیدنی و غذاهای مغذی زیاد بخورید.",
        "در حین کار، استراحت\u200Cهای کوتاهی داشته باشید تا ذهنتان آرام شود.",
        "برای بهبود خلق و خوی خود، زمانی را در طبیعت سپری کنید."
    )
    val randomTip = remember { tips.random() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { navToMoodRecording() }) { Text("ثبت احساسات") }

        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { navToExercise() }) { Text("تمرینات") }

        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { navToAdmin() }) { Text("ورود به بخش مدیریت") }

        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { navToTest() }) { Text("تست سلامت روان") }

        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { navToTestHistory() }) { Text("تاریخچه تست ها") }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            val offsetX = remember { Animatable(300f) } // start off-screen
            LaunchedEffect(randomTip) {
                offsetX.snapTo(300f)
                offsetX.animateTo(
                    targetValue = -300f,
                    animationSpec = tween(durationMillis = 20000)
                )
            }
            Text(
                text = randomTip,
                modifier = Modifier.offset(x = offsetX.value.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        if (moods.isNotEmpty()) {
            Text("حال و هوای این روزات :", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            moods.take(moods.size).forEach { mood ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(mood.moodType)
                        mood.note?.let { Text(it) }
                        Text(
                            SimpleDateFormat(
                                "dd/MM/yyyy",
                                Locale.getDefault()
                            ).format(Date(mood.date))
                        )
                    }
                }
            }
        }
    }
}