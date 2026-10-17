package com.rattel.app.ui.screens.settings

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rattel.app.core.utils.BackupRestoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val backupRestoreManager: BackupRestoreManager
) : ViewModel() {

    // حالة الوضع الداكن
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    // رسائل التنبيه (Snackbars) للنجاح أو الفشل
    private val _uiMessage = MutableStateFlow<String?>(null)
    val uiMessage: StateFlow<String?> = _uiMessage.asStateFlow()

    fun toggleDarkMode(enabled: Boolean) {
        _isDarkMode.value = enabled
        // في التطبيق الفعلي يتم حفظ هذه القيمة في DataStore أو SharedPreferences
    }

    fun performBackup(uri: Uri) {
        viewModelScope.launch {
            val success = backupRestoreManager.backupDatabase(uri)
            _uiMessage.value = if (success) "تم إنشاء النسخة الاحتياطية بنجاح!" else "فشل إنشاء النسخة الاحتياطية."
        }
    }

    fun performRestore(uri: Uri) {
        viewModelScope.launch {
            val success = backupRestoreManager.restoreDatabase(uri)
            _uiMessage.value = if (success) "تمت الاستعادة بنجاح. يرجى إعادة تشغيل التطبيق." else "فشلت الاستعادة."
        }
    }

    fun clearMessage() {
        _uiMessage.value = null
    }
}
