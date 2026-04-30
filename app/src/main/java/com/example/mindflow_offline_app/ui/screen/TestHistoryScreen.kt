package com.example.mindflow_offline_app.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mindflow_offline_app.viewmodel.TestHistoryViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TestHistoryScreen(viewModel: TestHistoryViewModel) {
    val results by viewModel.results.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.loadResults()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("تاریخچه تست ها", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(results) { result ->
                Card(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text("امتیاز: ${result.score}", style = MaterialTheme.typography.titleMedium)
                        Text(
                            "تاریخ: ${SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date(result.date))}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}