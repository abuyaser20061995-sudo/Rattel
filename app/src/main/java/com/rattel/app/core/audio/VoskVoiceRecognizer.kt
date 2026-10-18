package com.rattel.app.core.audio

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
//import org.vosk.Model
//import org.vosk.Recognizer
//import org.vosk.android.SpeechService
//import org.vosk.android.RecognitionListener
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VoskVoiceRecognizer @Inject constructor(
    @ApplicationContext private val context: Context
) : RecognitionListener {

    private var speechService: SpeechService? = null
    private var model: Model? = null

    private val _recognizedText = MutableStateFlow("")
    val recognizedText: StateFlow<String> = _recognizedText.asStateFlow()

    fun initModel(modelPath: String) {
        // تحميل النموذج الصوتي العربي من ملفات التطبيق المحلية
        // StorageService.unpack(context, "vosk-model-ar", "model", ... )
        // model = Model(path)
    }

    fun startListening() {
        if (model != null && speechService == null) {
            val recognizer = Recognizer(model, 16000.0f)
            speechService = SpeechService(recognizer, 16000.0f)
            speechService?.startListening(this)
        }
    }

    fun stopListening() {
        speechService?.stop()
        speechService = null
    }

    // دوال الاستماع الخاصة بمكتبة Vosk
    override fun onPartialResult(hypothesis: String?) {
        // نص غير مكتمل (أثناء النطق)
        hypothesis?.let { _recognizedText.value = extractTextFromJson(it) }
    }

    override fun onResult(hypothesis: String?) {
        // نص مكتمل (بعد صمت قصير)
        hypothesis?.let { _recognizedText.value = extractTextFromJson(it) }
    }

    override fun onFinalResult(hypothesis: String?) {}
    override fun onError(exception: Exception?) {}
    override fun onTimeout() {}

    private fun extractTextFromJson(json: String): String {
        // مكتبة Vosk تعيد النتائج بصيغة JSON {"text": "بسم الله"}
        // هنا يتم استخراج النص فقط
        return json // يتم استبدالها بكود استخراج فعلي بـ Gson أو Kotlinx.serialization
    }
}
