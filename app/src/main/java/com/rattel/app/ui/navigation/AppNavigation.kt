package com.rattel.app.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rattel.app.ui.screens.mushaf.MushafScreen
import com.rattel.app.ui.screens.imam.ImamSuggestionScreen
import com.rattel.app.ui.screens.tracking.VoiceTrackingScreen

object Routes {
    const val SPLASH = "splash_screen"
    const val HOME = "home_screen"
    const val MUSHAF = "mushaf_screen/{pageNumber}"
    const val IMAM_SUGGESTION = "imam_suggestion_screen"
    const val VOICE_TRACKING = "voice_tracking_screen"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        
        composable(route = Routes.HOME) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("تطبيق رتّل يعمل بنجاح!")
                Button(
                    onClick = { navController.navigate(Routes.IMAM_SUGGESTION) },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("فتح مساعد الإمام")
                }
            }
        }

        composable(
            route = Routes.MUSHAF,
            arguments = listOf(navArgument("pageNumber") { type = NavType.IntType })
        ) { backStackEntry ->
            val pageNumber = backStackEntry.arguments?.getInt("pageNumber") ?: 1
            MushafScreen(
                pageNumber = pageNumber,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(route = Routes.IMAM_SUGGESTION) {
            ImamSuggestionScreen(
                onBackClick = { navController.popBackStack() },
                onNavigateToMushaf = { page ->
                    navController.navigate("mushaf_screen/$page")
                }
            )
        }

        composable(route = Routes.VOICE_TRACKING) {
            VoiceTrackingScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
