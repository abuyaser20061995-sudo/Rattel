package com.rattel.app.ui.screens.mushaf

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rattel.app.data.local.entities.AyahEntity
import com.rattel.app.data.repository.QuranRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel الخاص بشاشة المصحف.
 * @HiltViewModel ليتم حقنه تلقائياً في الشاشة.
 */
@HiltViewModel
class MushafViewModel @Inject constructor(
    private val repository: QuranRepository,
    savedStateHandle: SavedStateHandle // لجلب المتغيرات الممررة عبر الـ Navigation (مثل رقم الصفحة)
) : ViewModel() {

    // استخراج رقم الصفحة الممرر من شاشة أخرى (الافتراضي صفحة 1)
    private val currentPageNumber: Int = savedStateHandle.get<Int>("pageNumber") ?: 1

    // حالة الواجهة: قائمة الآيات المعروضة حالياً في الصفحة
    private val _pageAyahs = MutableStateFlow<List<AyahEntity>>(emptyList())
    val pageAyahs: StateFlow<List<AyahEntity>> = _pageAyahs.asStateFlow()

    // حالة الواجهة: الآية المحددة حالياً (عند الضغط المطول)
    private val _selectedAyah = MutableStateFlow<AyahEntity?>(null)
    val selectedAyah: StateFlow<AyahEntity?> = _selectedAyah.asStateFlow()

    init {
        // عند فتح الشاشة، قم بتحميل بيانات الصفحة فوراً
        loadPageData(currentPageNumber)
    }

    private fun loadPageData(page: Int) {
        viewModelScope.launch {
            repository.getAyahsByPage(page)
                .catch { e ->
                    // هنا يمكننا معالجة الأخطاء (مثل إظهار رسالة للمستخدم)
                    e.printStackTrace()
                }
                .collect { ayahs ->
                    _pageAyahs.value = ayahs
                }
        }
    }

    /**
     * تُستدعى من الواجهة عندما يضغط المستخدم مطولاً على آية.
     */
    fun onAyahLongPressed(ayahId: Int) {
        viewModelScope.launch {
            val ayah = repository.getAyahById(ayahId)
            _selectedAyah.value = ayah
        }
    }

    /**
     * إغلاق القائمة المنبثقة وإلغاء تحديد الآية.
     */
    fun clearSelectedAyah() {
        _selectedAyah.value = null
    }
}
