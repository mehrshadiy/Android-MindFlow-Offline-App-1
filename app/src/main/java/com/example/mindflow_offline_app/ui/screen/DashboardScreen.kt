package com.example.mindflow_offline_app.ui.screen

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mindflow_offline_app.ui.components.DashboardButton
import com.example.mindflow_offline_app.ui.components.MoodCard
import com.example.mindflow_offline_app.ui.theme.*
import com.example.mindflow_offline_app.viewmodel.MoodViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
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
        "برای تجدید قوای ذهنی و جسمانی، کمی پیاده‌روی کنید.",
        "به مدت ۵ دقیقه تنفس عمیق را تمرین کنید.",
        "سه چیزی را که بابت آنها سپاسگزار هستید را بنویسید.",
        "برای کاهش استرس به موسیقی آرامش‌بخش گوش دهید.",
        "نوشیدنی و غذاهای مغذی زیاد بخورید.",
        "در حین کار، استراحت‌های کوتاهی داشته باشید تا ذهنتان آرام شود.",
        "برای بهبود خلق و خوی خود، زمانی را در طبیعت سپری کنید."
    )
    val randomTip = remember { tips.random() }
    
    val infiniteTransition = rememberInfiniteTransition(label = "tip")
    val offsetX by infiniteTransition.animateFloat(
        initialValue = 1000f,
        targetValue = -1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "tipScroll"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "MindFlow",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "سلامت روان شما",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                actions = {
                    IconButton(onClick = navToAdmin) {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = "تنظیمات",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.tertiaryContainer)
                        .padding(8.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = "💡 $randomTip",
                        modifier = Modifier.offset(x = offsetX.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            
            item {
                Text(
                    text = "دسترسی سریع",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            
            item {
                DashboardButton(
                    text = "ثبت احساسات",
                    icon = Icons.Default.Mood,
                    onClick = navToMoodRecording,
                    gradientColors = listOf(GradientPinkStart, GradientPinkEnd)
                )
            }
            
            item {
                DashboardButton(
                    text = "تمرینات آرامش‌بخش",
                    icon = Icons.Default.SelfImprovement,
                    onClick = navToExercise,
                    gradientColors = listOf(GradientGreenStart, GradientGreenEnd)
                )
            }
            
            item {
                DashboardButton(
                    text = "تست سلامت روان",
                    icon = Icons.Default.Psychology,
                    onClick = navToTest,
                    gradientColors = listOf(GradientTealStart, GradientTealEnd)
                )
            }
            
            item {
                DashboardButton(
                    text = "تاریخچه تست‌ها",
                    icon = Icons.Default.History,
                    onClick = navToTestHistory,
                    gradientColors = listOf(Purple40, Purple80)
                )
            }
            
            if (moods.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "حال و هوای اخیر شما",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                
                items(moods.take(5)) { mood ->
                    val moodColor = when (mood.moodType) {
                        "عالی" -> MoodExcellent
                        "خوب" -> MoodGood
                        "معمولی" -> MoodNormal
                        "بد" -> MoodBad
                        "خیلی بد" -> MoodVeryBad
                        else -> MoodNormal
                    }
                    
                    MoodCard(
                        mood = mood.moodType,
                        note = mood.note,
                        date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(mood.date)),
                        moodColor = moodColor
                    )
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
