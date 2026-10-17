package com.rattel.app.ui.screens.tafseer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TafseerScreen(
    onBackClick: () -> Unit,
    viewModel: TafseerViewModel = hiltViewModel()
) {
    val contentType by viewModel.contentType.collectAsStateWithLifecycle()
    val contentText by viewModel.contentText.collectAsStateWithLifecycle()
    val fontSize by viewModel.fontSize.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("البيان والتدبر") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) { Text("←") }
                },
                actions = {
                    IconButton(onClick = { viewModel.decreaseFontSize() }) { Text("A-") }
                    IconButton(onClick = { viewModel.increaseFontSize() }) { Text("A+") }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1B4332),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // أزرار التبديل بين التفسير والإعراب
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                TabRow(
                    selectedTabIndex = if (contentType == ContentType.TAFSEER) 0 else 1,
                    modifier = Modifier.width(200.dp)
                ) {
                    Tab(
                        selected = contentType == ContentType.TAFSEER,
                        onClick = { viewModel.setContentType(ContentType.TAFSEER) },
                        text = { Text("التفسير") }
                    )
                    Tab(
                        selected = contentType == ContentType.IRAB,
                        onClick = { viewModel.setContentType(ContentType.IRAB) },
                        text = { Text("الإعراب") }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // عرض النص
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F4F0)),
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = contentText,
                    fontSize = fontSize.sp,
                    lineHeight = (fontSize * 1.5).sp,
                    modifier = Modifier
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                )
            }
        }
    }
}
