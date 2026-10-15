package com.rattel.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import com.rattel.app.ui.navigation.AppNavigation
import com.rattel.app.ui.theme.RattelTheme

/**
 * الواجهة الرئيسية التي تحمل التطبيق.
 * @AndroidEntryPoint تسمح لهذه الواجهة باستقبال البيانات (ViewModels) من Hilt تلقائياً.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // تفعيل رسم واجهة المستخدم باستخدام Jetpack Compose
        setContent {
            // RattelTheme: ملف الألوان والخطوط الذي صممناه في المرحلة السابقة
            RattelTheme {
                // حاوية الشاشة كاملة
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // استدعاء ملف التنقل الذي يدير جميع شاشات التطبيق
                    AppNavigation()
                }
            }
        }
    }
}
