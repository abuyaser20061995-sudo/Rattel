package com.rattel.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
//import com.rattel.app.ui.screens.home.HomeScreen
import com.rattel.app.ui.screens.mushaf.MushafScreen
import com.rattel.app.ui.screens.imam.ImamSuggestionScreen
import com.rattel.app.ui.screens.tracking.VoiceTrackingScreen

/**
 * كائن يحتوي على أسماء مسارات الشاشات (Routes) لتجنب الأخطاء الإملائية.
 */
object Routes {
    const val SPLASH = "splash_screen"
    const val HOME = "home_screen"
    const val MUSHAF = "mushaf_screen/{pageNumber}" // تمرير رقم الصفحة كمتغير
    const val IMAM_SUGGESTION = "imam_suggestion_screen"
    const val VOICE_TRACKING = "voice_tracking_screen"
}

@Composable
fun AppNavigation() {
    // كائن للتحكم في التنقل
    val navController = rememberNavController()

    // NavHost هو الحاوية التي تعرض الشاشة المناسبة بناءً على المسار الحالي
    NavHost(
        navController = navController,
        startDestination = Routes.HOME // جعلنا الشاشة الرئيسية هي البداية لتسهيل التطوير (لاحقاً نجعلها Splash)
    ) {
        
        // 1. الشاشة الرئيسية
        composable(route = Routes.HOME) {
            // HomeScreen(
               // onNavigateToMushaf = { page ->
                    // الانتقال إلى المصحف مع تحديد رقم الصفحة
                    navController.navigate("mushaf_screen/$page")
                },
                onNavigateToImamSection = {
                    navController.navigate(Routes.IMAM_SUGGESTION)
                },
                onNavigateToVoiceTracking = {
                    navController.navigate(Routes.VOICE_TRACKING)
                }
            )
        }

        // 2. شاشة المصحف (تحتاج لمعرفة رقم الصفحة المفتوحة)
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

        // 3. شاشة "ماذا أقرأ؟" الخاصة بالأئمة
        composable(route = Routes.IMAM_SUGGESTION) {
            ImamSuggestionScreen(
                onBackClick = { navController.popBackStack() },
                onNavigateToMushaf = { page ->
                    navController.navigate("mushaf_screen/$page")
                }
            )
        }

        // 4. شاشة التتبع الصوتي والتسميع (الميزة الأقوى)
        composable(route = Routes.VOICE_TRACKING) {
            VoiceTrackingScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
