package com.rattel.app.ui.screens.mushaf

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MushafScreen(pageNumber: Int, onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("جاري تجهيز المصحف...", fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) { Icon(Icons.Default.ArrowBack, contentDescription = "رجوع") }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().background(Color(0xFFFAF7F0)).padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text("سيتم ربط هذه الشاشة بقاعدة بيانات القرآن الحقيقية في الخطوة القادمة.", textAlign = TextAlign.Center, modifier = Modifier.padding(32.dp))
        }
    }
}
