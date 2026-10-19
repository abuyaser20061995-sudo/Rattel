package com.rattel.app.core.audio

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class VoskVoiceRecognizer @Inject constructor(
    @ApplicationContext private val context: Context
) {
}
