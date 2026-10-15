package com.rattel.app.ui.screens.mushaf

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

/**
 * الشاشة الرئيسية للمصحف
 * تقوم بربط واجهة المستخدم (UI) مع مدير الحالة (ViewModel)
 */
@Composable
fun MushafScreen(
    pageNumber: Int,
    onBackClick: () -> Unit,
    viewModel: MushafViewModel = hiltViewModel()
) {
    // مراقبة البيانات القادمة من الـ ViewModel
    val pageAyahs by viewModel.pageAyahs.collectAsStateWithLifecycle()
    val selectedAyah by viewModel.selectedAyah.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFFFFFF0))) { // لون عاجي مريح للعين
        
        // رسم صفحة المصحف المطابقة للورقي
        MushafPage(
            pageNumber = pageNumber,
            // سيتم تمرير الإحداثيات هنا لاحقاً لمعرفة مكان كل آية على الصورة
            ayahBounds = emptyList(), 
            onAyahLongPress = { ayahId -> 
                viewModel.onAyahLongPressed(ayahId) 
            }
        )

        // إظهار القائمة السفلية إذا تم تحديد آية (الضغط المطول)
        selectedAyah?.let { ayah ->
            AyahActionsSheet(
                ayahId = ayah.id,
                surahAndAyah = "سورة ${ayah.surahNumber} - آية ${ayah.ayahNumber}",
                onDismiss = { viewModel.clearSelectedAyah() },
                onListen = { /* سيتم ربطه بمشغل الصوت لاحقاً */ },
                onTafseer = { /* سيتم ربطه بشاشة التفسير لاحقاً */ },
                onIrab = { /* سيتم ربطه بشاشة الإعراب لاحقاً */ },
                onSaveToReview = { /* سيتم ربطه بنظام الحفظ لاحقاً */ }
            )
        }
    }
}

/**
 * مكون عرض صورة المصحف مع طبقة اللمس التفاعلية
 */
@Composable
fun MushafPage(
    pageNumber: Int,
    ayahBounds: List<Any>, // استخدمنا Any مؤقتاً حتى ننشئ كلاس الإحداثيات
    onAyahLongPress: (ayahId: Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { offset ->
                        // هنا سيتم حساب الـ x و y لمعرفة أي آية تم لمسها
                        // مؤقتاً سنقوم بتمرير رقم افتراضي لتجربة الواجهة
                        val clickedAyahId = 1 
                        onAyahLongPress(clickedAyahId)
                    }
                )
            }
    ) {
        // صورة الصفحة (تحتاج لإضافة صور المصحف لاحقاً في مجلد drawable)
        // Image(
        //     painter = painterResource(id = getPageDrawableId(pageNumber)),
        //     contentDescription = "صفحة المصحف رقم $pageNumber",
        //     modifier = Modifier.fillMaxSize(),
        //     contentScale = ContentScale.Fit
        // )
        
        // مؤقت ريثما تتم إضافة الصور:
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Text(
                text = "صفحة المصحف رقم: $pageNumber",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF1B4332) // أخضر داكن
            )
            Text(
                text = "(اضغط مطولاً هنا لتجربة القائمة)",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}

/**
 * القائمة المنبثقة التي تظهر عند الضغط المطول على آية
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AyahActionsSheet(
    ayahId: Int,
    surahAndAyah: String,
    onDismiss: () -> Unit,
    onListen: () -> Unit,
    onTafseer: () -> Unit,
    onIrab: () -> Unit,
    onSaveToReview: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp, start = 16.dp, end = 16.dp)
        ) {
            // عنوان القائمة
            Text(
                text = surahAndAyah,
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF1B4332),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            Divider(color = Color.LightGray, thickness = 0.5.dp)
            Spacer(modifier = Modifier.height(8.dp))

            // الخيارات
            ActionRow(title = "استماع للتلاوة", onClick = onListen)
            ActionRow(title = "التفسير", onClick = onTafseer)
            ActionRow(title = "إعراب القرآن", onClick = onIrab)
            ActionRow(title = "إضافة للمراجعة (حفظ)", onClick = onSaveToReview)
        }
    }
}

/**
 * صف واحد داخل القائمة المنبثقة
 */
@Composable
fun ActionRow(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
