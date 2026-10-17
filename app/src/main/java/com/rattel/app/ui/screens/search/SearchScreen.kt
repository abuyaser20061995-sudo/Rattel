package com.rattel.app.ui.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onNavigateToMushaf: (pageNumber: Int) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        
        // شريط البحث
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.onSearchQueryChanged(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("ابحث عن آية أو سورة...") },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF1B4332),
                cursorColor = Color(0xFF1B4332)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (searchQuery.isNotBlank()) {
            // عرض نتائج البحث
            Text("نتائج البحث (${searchResults.size})", color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(searchResults) { ayah ->
                    SearchResultItem(ayah = ayah, onClick = { onNavigateToMushaf(ayah.pageNumber) })
                }
            }
        } else {
            // عرض فهرس السور (قائمة وهمية كمثال)
            Text("فهرس السور", style = MaterialTheme.typography.titleLarge, color = Color(0xFF1B4332))
            Spacer(modifier = Modifier.height(8.dp))
            
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(114) { index ->
                    SurahListItem(
                        surahNumber = index + 1,
                        surahName = "سورة ${index + 1}", // سيتم استبدالها بأسماء السور الحقيقية
                        pageNumber = (index * 5) + 1, // صفحة وهمية للتبسيط
                        onClick = { page -> onNavigateToMushaf(page) }
                    )
                }
            }
        }
    }
}

@Composable
fun SearchResultItem(ayah: com.rattel.app.data.local.entities.AyahEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F4F0))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = ayah.textUthmani,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "سورة ${ayah.surahNumber} - آية ${ayah.ayahNumber} - صفحة ${ayah.pageNumber}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun SurahListItem(surahNumber: Int, surahName: String, pageNumber: Int, onClick: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(pageNumber) }
            .padding(vertical = 12.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Text("$surahNumber.", color = Color.Gray, modifier = Modifier.width(32.dp))
            Text(surahName, style = MaterialTheme.typography.titleMedium)
        }
        Text("صفحة $pageNumber", color = Color.Gray)
    }
}
