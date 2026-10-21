package com.rattel.app.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToMushaf: (Int) -> Unit,
    onNavigateToImamSection: () -> Unit,
    onNavigateToVoiceTracking: () -> Unit
) {
    // حالة للتحكم في شريط التنقل السفلي
    var selectedBottomTab by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("القرآن الكريم", fontWeight = FontWeight.Bold, fontSize = 22.sp) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.List, contentDescription = "الفهرس") },
                    label = { Text("الفهرس") },
                    selected = selectedBottomTab == 0,
                    onClick = { selectedBottomTab = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "مساعد الإمام") },
                    label = { Text("مساعد الإمام") },
                    selected = selectedBottomTab == 1,
                    onClick = {
                        selectedBottomTab = 1
                        onNavigateToImamSection()
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.PlayArrow, contentDescription = "التتبع الصوتي") },
                    label = { Text("التتبع الصوتي") },
                    selected = selectedBottomTab == 2,
                    onClick = {
                        selectedBottomTab = 2
                        onNavigateToVoiceTracking()
                    }
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            // بيانات وهمية مؤقتة لمحاكاة شكل الفهرس الكلاسيكي
            val surahs = listOf(
                Triple("الفاتحة", "مكية • ٧ آيات", "١"),
                Triple("البقرة", "مدنية • ٢٨٦ آية", "٢"),
                Triple("آل عمران", "مدنية • ٢٠٠ آية", "٥٠"),
                Triple("النساء", "مدنية • ١٧٦ آية", "٧٧"),
                Triple("المائدة", "مدنية • ١٢٠ آية", "١٠٦")
            )

            items(surahs.size) { index ->
                val surah = surahs[index]
                ClassicSurahItem(
                    number = index + 1,
                    name = surah.first,
                    details = surah.second,
                    page = surah.third,
                    onClick = { onNavigateToMushaf(1) } // مؤقتاً تفتح الصفحة الأولى
                )
                // خط رفيع جداً للفصل بين السور كما في المصاحف الرسمية
                Divider(
                    thickness = 0.5.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
fun ClassicSurahItem(number: Int, name: String, details: String, page: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // مكان رقم السورة (لاحقاً نضع هنا الزخرفة الإسلامية)
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(40.dp)
        ) {
            Text(
                text = number.toString(),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // اسم السورة وتفاصيلها
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "سورة $name",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = details,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // رقم صفحة بداية السورة
        Text(
            text = page,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
