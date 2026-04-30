package com.example.mindflow_offline_app.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MentalHealthTestResultScreen(score: Int, onRestart: () -> Unit) {
    val resultText = when (score) {
        in 0..5 -> "سلامت روان فوق\u200Cالعاده عالی! همینطوری ادامه بده!"
        in 6..10 -> "نگرانی\u200Cهای خفیف. کمی مراقبت از خود را در نظر بگیرید."
        in 11..13 -> "استرس/اضطراب متوسط. تمرینات آرامش\u200Cبخش را امتحان کنید."
        else -> "استرس زیاد! کمک حرفه\u200Cای را در نظر بگیرید."
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "امتیاز شما: $score",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = resultText,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onRestart) {
            Text("تست مجدد")
        }
    }
}