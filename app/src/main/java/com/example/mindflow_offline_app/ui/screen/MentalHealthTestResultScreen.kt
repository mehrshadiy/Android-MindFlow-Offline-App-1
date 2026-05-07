package com.example.mindflow_offline_app.ui.screen

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mindflow_offline_app.ui.theme.*

@Composable
fun MentalHealthTestResultScreen(score: Int, onDone: () -> Unit) {
    val (statusText, statusColor, statusIcon, description, recommendations) = when {
        score < 10 -> TestResult(
            "عالی",
            MoodExcellent,
            Icons.Default.SentimentVerySatisfied,
            "سلامت روان شما در وضعیت بسیار خوبی قرار دارد. شما احساس آرامش و رضایت می‌کنید.",
            listOf(
                "این وضعیت را حفظ کنید",
                "به فعالیت‌های روزانه خود ادامه دهید",
                "با دیگران ارتباط داشته باشید",
                "از لحظات خوب لذت ببرید"
            )
        )
        score < 20 -> TestResult(
            "خوب",
            MoodGood,
            Icons.Default.SentimentSatisfied,
            "سلامت روان شما در وضعیت خوبی است. ممکن است گاهی استرس کمی داشته باشید اما به طور کلی احساس خوبی دارید.",
            listOf(
                "به ورزش منظم ادامه دهید",
                "خواب کافی داشته باشید",
                "با دوستان و خانواده وقت بگذرانید",
                "تمرینات تنفسی انجام دهید"
            )
        )
        score < 30 -> TestResult(
            "متوسط",
            MoodNormal,
            Icons.Default.SentimentNeutral,
            "سلامت روان شما در حد متوسط است. ممکن است گاهی احساس استرس یا اضطراب کنید.",
            listOf(
                "تمرینات آرامش‌بخش انجام دهید",
                "برنامه روزانه منظم داشته باشید",
                "از فعالیت‌های مورد علاقه خود لذت ببرید",
                "با افراد مورد اعتماد صحبت کنید"
            )
        )
        score < 40 -> TestResult(
            "نیاز به توجه",
            MoodBad,
            Icons.Default.SentimentDissatisfied,
            "ممکن است با چالش‌های روانی مواجه باشید. توصیه می‌شود به سلامت روان خود توجه بیشتری کنید.",
            listOf(
                "با یک مشاور یا روانشناس مشورت کنید",
                "تمرینات ذهن‌آگاهی انجام دهید",
                "از حمایت خانواده و دوستان استفاده کنید",
                "فعالیت بدنی منظم داشته باشید"
            )
        )
        else -> TestResult(
            "نیاز به مشاوره",
            MoodVeryBad,
            Icons.Default.SentimentVeryDissatisfied,
            "نتایج نشان می‌دهد که ممکن است با مشکلات روانی جدی مواجه باشید. حتماً با یک متخصص مشورت کنید.",
            listOf(
                "هر چه سریع‌تر با روانشناس یا روانپزشک مشورت کنید",
                "از خدمات مشاوره بهره‌مند شوید",
                "با افراد نزدیک خود صحبت کنید",
                "به خودتان زمان دهید و عجله نکنید"
            )
        )
    }

    val animatedScore = remember { Animatable(0f) }
    
    LaunchedEffect(score) {
        animatedScore.animateTo(
            targetValue = score.toFloat(),
            animationSpec = tween(durationMillis = 1500, easing = FastOutSlowInEasing)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier
                .size(200.dp)
                .shadow(16.dp, CircleShape),
            shape = CircleShape,
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.radialGradient(
                            listOf(statusColor, statusColor.copy(alpha = 0.7f))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = statusIcon,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(80.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${animatedScore.value.toInt()}",
                        color = Color.White,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = statusText,
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            color = statusColor
        )

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp, RoundedCornerShape(20.dp)),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "توضیحات",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = 28.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp, RoundedCornerShape(20.dp)),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Lightbulb,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "توصیه‌ها",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                recommendations.forEach { recommendation ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary)
                                .align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = recommendation,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onDone,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "بازگشت به صفحه اصلی",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

data class TestResult(
    val status: String,
    val color: Color,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val description: String,
    val recommendations: List<String>
)
