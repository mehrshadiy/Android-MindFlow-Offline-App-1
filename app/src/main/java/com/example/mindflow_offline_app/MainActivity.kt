package com.example.mindflow_offline_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mindflow_offline_app.ui.screen.AdminScreen
import com.example.mindflow_offline_app.ui.screen.DashboardScreen
import com.example.mindflow_offline_app.ui.screen.ExerciseScreen
import com.example.mindflow_offline_app.ui.screen.MentalHealthTestResultScreen
import com.example.mindflow_offline_app.ui.screen.MentalHealthTestScreen
import com.example.mindflow_offline_app.ui.screen.MoodRecordingScreen
import com.example.mindflow_offline_app.ui.screen.TestHistoryScreen
import com.example.mindflow_offline_app.ui.theme.MindFlowTheme
import com.example.mindflow_offline_app.viewmodel.AdminViewModel
import com.example.mindflow_offline_app.viewmodel.ExerciseViewModel
import com.example.mindflow_offline_app.viewmodel.MoodViewModel
import com.example.mindflow_offline_app.viewmodel.TestHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                MindFlowTheme {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "dashboard") {
                        composable("dashboard") {
                            val moodViewModel: MoodViewModel = hiltViewModel()
                            DashboardScreen(
                                moodViewModel = moodViewModel,
                                navToAdmin = { navController.navigate("admin") },
                                navToTest = { navController.navigate("mentalHealthTest") },
                                navToTestHistory = { navController.navigate("testHistory") },
                                navToMoodRecording = { navController.navigate("moodRecording") },
                                navToExercise = { navController.navigate("exercise") },
                            )
                        }
                        composable("moodRecording") {
                            val moodViewModel: MoodViewModel = hiltViewModel()
                            MoodRecordingScreen(
                                moodViewModel = moodViewModel,
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable("exercise") {
                            val exerciseViewModel: ExerciseViewModel = hiltViewModel()
                            ExerciseScreen(
                                exerciseViewModel = exerciseViewModel,
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable("admin") {
                            val adminViewModel: AdminViewModel = hiltViewModel()
                            AdminScreen(
                                adminViewModel = adminViewModel,
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable("mentalHealthTest") {
                            MentalHealthTestScreen(
                                onTestComplete = { score ->
                                    navController.navigate("mentalHealthResult/$score") {
                                        popUpTo("mentalHealthTest") { inclusive = true }
                                    }
                                },
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable(
                            "mentalHealthResult/{score}",
                            arguments = listOf(navArgument("score") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val score = backStackEntry.arguments?.getInt("score") ?: 0
                            MentalHealthTestResultScreen(score = score) {
                                navController.navigate("dashboard") {
                                    popUpTo("dashboard") { inclusive = false }
                                }
                            }
                        }
                        composable("testHistory") {
                            val historyViewModel: TestHistoryViewModel = hiltViewModel()
                            TestHistoryScreen(
                                viewModel = historyViewModel,
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
