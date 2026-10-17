package com.rattel.app.ui.screens.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rattel.app.data.local.entities.ReviewItemEntity
import com.rattel.app.data.repository.QuranRepository
import com.rattel.app.domain.usecase.ReviewQuality
import com.rattel.app.domain.usecase.SpacedRepetitionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val quranRepository: QuranRepository,
    private val spacedRepetitionUseCase: SpacedRepetitionUseCase
    // ملاحظة: يُفترض وجود ReviewRepository لجلب المقاطع من جدول memorization_review
) : ViewModel() {

    // قائمة المقاطع المستحقة للمراجعة اليوم
    private val _pendingReviews = MutableStateFlow<List<ReviewItemEntity>>(emptyList())
    val pendingReviews: StateFlow<List<ReviewItemEntity>> = _pendingReviews.asStateFlow()

    // المقطع المعروض حالياً للمراجعة
    private val _currentItem = MutableStateFlow<ReviewItemEntity?>(null)
    val currentItem: StateFlow<ReviewItemEntity?> = _currentItem.asStateFlow()

    init {
        loadPendingReviews()
    }

    private fun loadPendingReviews() {
        viewModelScope.launch {
            // كود افتراضي: جلب المقاطع التي تاريخ مراجعتها أصغر من أو يساوي اليوم
            // val items = reviewRepository.getDueReviews(System.currentTimeMillis())
            // _pendingReviews.value = items
            // _currentItem.value = items.firstOrNull()
        }
    }

    fun submitReview(quality: ReviewQuality) {
        val item = _currentItem.value ?: return

        // حساب الموعد الجديد
        val result = spacedRepetitionUseCase.calculateNextReview(
            currentIntervalDays = item.intervalDays,
            currentEaseFactor = item.easeFactor,
            quality = quality
        )

        // إنشاء نسخة محدثة من المقطع
        val updatedItem = item.copy(
            intervalDays = result.newIntervalDays,
            easeFactor = result.newEaseFactor,
            nextReviewDate = result.nextReviewTimestamp
        )

        viewModelScope.launch {
            // تحديث قاعدة البيانات
            // reviewRepository.updateReviewItem(updatedItem)
            
            // الانتقال للمقطع التالي
            val currentList = _pendingReviews.value.toMutableList()
            if (currentList.isNotEmpty()) {
                currentList.removeAt(0)
                _pendingReviews.value = currentList
                _currentItem.value = currentList.firstOrNull()
            }
        }
    }
}
