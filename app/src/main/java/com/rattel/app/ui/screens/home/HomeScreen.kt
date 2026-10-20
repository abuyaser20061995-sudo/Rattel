package com.rattel.app.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

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
            TopAppBar(
                title = { Text("القرآن الكريم", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Book, contentDescription = "المصحف") },
                    label = { Text("المصحف") },
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
                    icon = { Icon(Icons.Default.Mic, contentDescription = "التتبع الصوتي") },
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
        
        // محتوى الشاشة الرئيسية (فهرس السور)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // بطاقة المتابعة (آخر قراءة)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable { onNavigateToMushaf(1) },
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(20.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("متابعة القراءة", color = Color.White.copy(alpha = 0.8f), style = MaterialTheme.typography.labelMedium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("سورة الفاتحة", color = Color.White, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Text("الآية ١", color = Color.White.copy(alpha = 0.8f), style = MaterialTheme.typography.bodyMedium)
                    }
                    Icon(Icons.Default.Book, contentDescription = null, tint = Color.White, modifier = Modifier.size(40.dp))
                }
            }

            Text(
                text = "الفهرس",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )

            // قائمة السور (وهمية مؤقتاً للتصميم)
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val surahs = listOf("الفاتحة", "البقرة", "آل عمران", "النساء", "المائدة")
                items(surahs.size) { index ->
                    SurahItem(
                        number = index + 1,
                        name = surahs[index],
                        onClick = { onNavigateToMushaf(1) } // مؤقتاً تفتح الصفحة الأولى
                    )
                }
            }
        }
    }
}

@Composable
fun SurahItem(number: Int, name: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // رقم السورة
        Box(
            modifier = Modifier.size(40.dp).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(number.toString(), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("مكية • آياتها ...", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
        }
        
        Text("الاسم بالرسم العثماني", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
    }
}
