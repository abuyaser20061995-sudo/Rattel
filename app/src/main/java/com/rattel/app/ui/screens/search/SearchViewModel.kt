package com.rattel.app.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rattel.app.data.local.entities.AyahEntity
import com.rattel.app.data.repository.QuranRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: QuranRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<List<AyahEntity>>(emptyList())
    val searchResults: StateFlow<List<AyahEntity>> = _searchResults.asStateFlow()

    init {
        // نراقب التغيرات في مربع البحث مع تأخير بسيط (Debounce) لتقليل الضغط على قاعدة البيانات
        viewModelScope.launch {
            _searchQuery
                // .debounce(300) // يتطلب إضافة مكتبة coroutines-core للـ debounce (اختياري)
                .collectLatest { query ->
                    if (query.isNotBlank() && query.length >= 2) {
                        repository.searchQuran(query).collect { results ->
                            _searchResults.value = results
                        }
                    } else {
                        _searchResults.value = emptyList()
                    }
                }
        }
    }

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }
}
