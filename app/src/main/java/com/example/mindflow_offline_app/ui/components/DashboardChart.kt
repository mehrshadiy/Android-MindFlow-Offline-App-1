package com.example.mindflow_offline_app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.example.mindflow_offline_app.data.db.TestResult
import com.example.mindflow_offline_app.data.model.Mood
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.max

private data class ChartItem(
    val value: Float,
    val label: String,
    val statusText: String,
    val color: Color
)

@Composable
fun DashboardChart(
    moods: List<Mood>,
    testResults: List<TestResult>
) {
    var selectedTab by remember { mutableStateOf("mood") }

    val moodItems = moods
        .sortedBy { it.date }
        .takeLast(30)
        .map {
            val value = when (it.moodType) {
                "عالی" -> 5f
                "خوب" -> 4f
                "معمولی" -> 3f
                "بد" -> 2f
                "خیلی بد" -> 1f
                else -> 3f
            }

            val color = when (it.moodType) {
                "عالی", "خوب" -> Color(0xFF2E7D32)
                "معمولی" -> Color(0xFFF9A825)
                "بد", "خیلی بد" -> Color(0xFFC62828)
                else -> Color(0xFF1565C0)
            }

            ChartItem(
                value = value,
                label = formatDate(it.date),
                statusText = it.moodType,
                color = color
            )
        }

    val testItems = testResults
        .sortedBy { it.id }
        .takeLast(30)
        .mapIndexed { index, result ->
            val status = when {
                result.score >= 80 -> "خیلی خوب"
                result.score >= 60 -> "خوب"
                result.score >= 40 -> "متوسط"
                result.score >= 20 -> "ضعیف"
                else -> "خیلی ضعیف"
            }

            val color = when {
                result.score >= 80 -> Color(0xFF2E7D32)
                result.score >= 60 -> Color(0xFF558B2F)
                result.score >= 40 -> Color(0xFFF9A825)
                result.score >= 20 -> Color(0xFFEF6C00)
                else -> Color(0xFFC62828)
            }

            ChartItem(
                value = result.score.toFloat(),
                label = "تست ${index + 1}",
                statusText = "$status (${result.score})",
                color = color
            )
        }

    val items = if (selectedTab == "mood") moodItems else testItems
    val yLabels = if (selectedTab == "mood") {
        listOf("1", "2", "3", "4", "5")
    } else {
        listOf("0", "25", "50", "75", "100")
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Text(
                text = "نمودار وضعیت",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                FilterChip(
                    selected = selectedTab == "mood",
                    onClick = { selectedTab = "mood" },
                    label = { Text("حال‌و‌هوا") }
                )

                FilterChip(
                    selected = selectedTab == "test",
                    onClick = { selectedTab = "test" },
                    label = { Text("نتایج تست") }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (items.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outlineVariant,
                            shape = RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (selectedTab == "mood")
                            "هیچ دیتایی برای حال‌و‌هوا ثبت نشده است."
                        else
                            "هیچ نتیجه تستی برای نمایش وجود ندارد.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                return@Column
            }

            val maxValue = if (selectedTab == "mood") 5f else max(100f, items.maxOf { it.value })

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier
                        .height(260.dp)
                        .padding(top = 8.dp, bottom = 28.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    yLabels.reversed().forEach { label ->
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .horizontalScroll(rememberScrollState())
                ) {
                    val chartWidth = max(420.dp, (items.size * 56).dp)

                    Column {
                        Box(
                            modifier = Modifier
                                .width(chartWidth)
                                .height(260.dp)
                        ) {
                            Canvas(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                val leftPadding = 24f
                                val topPadding = 16f
                                val bottomPadding = 36f
                                val rightPadding = 16f

                                val chartW = size.width - leftPadding - rightPadding
                                val chartH = size.height - topPadding - bottomPadding

                                // grid lines
                                val gridCount = 5
                                for (i in 0 until gridCount) {
                                    val y = topPadding + (chartH / (gridCount - 1)) * i
                                    drawLine(
                                        color = Color(0xFFE0E0E0),
                                        start = Offset(leftPadding, y),
                                        end = Offset(leftPadding + chartW, y),
                                        strokeWidth = 1f
                                    )
                                }

                                // axis
                                drawLine(
                                    color = Color.Gray,
                                    start = Offset(leftPadding, topPadding),
                                    end = Offset(leftPadding, topPadding + chartH),
                                    strokeWidth = 2f
                                )

                                drawLine(
                                    color = Color.Gray,
                                    start = Offset(leftPadding, topPadding + chartH),
                                    end = Offset(leftPadding + chartW, topPadding + chartH),
                                    strokeWidth = 2f
                                )

                                if (items.isNotEmpty()) {
                                    val spacing = if (items.size == 1) {
                                        chartW / 2f
                                    } else {
                                        chartW / (items.size - 1)
                                    }

                                    val points = items.mapIndexed { index, item ->
                                        val x = if (items.size == 1) {
                                            leftPadding + chartW / 2f
                                        } else {
                                            leftPadding + spacing * index
                                        }

                                        val normalized = (item.value / maxValue).coerceIn(0f, 1f)
                                        val y = topPadding + chartH - (normalized * chartH)
                                        Offset(x, y)
                                    }

                                    if (points.size > 1) {
                                        val path = Path().apply {
                                            moveTo(points.first().x, points.first().y)
                                            for (i in 1 until points.size) {
                                                lineTo(points[i].x, points[i].y)
                                            }
                                        }

                                        drawPath(
                                            path = path,
                                            color = Color(0xFF7C4DFF),
                                            style = Stroke(width = 4f, cap = StrokeCap.Round)
                                        )
                                    }

                                    points.forEachIndexed { index, point ->
                                        drawCircle(
                                            color = items[index].color,
                                            radius = 8f,
                                            center = point
                                        )
                                        drawCircle(
                                            color = Color.White,
                                            radius = 4f,
                                            center = point
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.width(chartWidth),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            items.forEach { item ->
                                Box(
                                    modifier = Modifier.width(40.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = item.label,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "جزئیات آخرین داده‌ها",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items.takeLast(5).reversed().forEach { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .background(item.color, shape = RoundedCornerShape(50))
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = item.label,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = item.statusText,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = item.color
                                )
                            }
                        }

                        Text(
                            text = if (selectedTab == "mood")
                                "امتیاز ${item.value.toInt()} از 5"
                            else
                                "امتیاز ${item.value.toInt()}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

private fun formatDate(timestamp: Long): String {
    return try {
        SimpleDateFormat("MM/dd", Locale.getDefault()).format(Date(timestamp))
    } catch (e: Exception) {
        "-"
    }
}
