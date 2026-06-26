package com.digiwizkid.islandhopper.data.repository

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

internal class TextToSpeechManager(context: Context) {

    private var tts: TextToSpeech? = null
    private var isInitialized = false
    private var isMusicOn = true
    private var pendingText: String? = null

    init {
        tts = TextToSpeech(context) { status ->
            isInitialized = status == TextToSpeech.SUCCESS
            if (isInitialized) {
                tts?.language = Locale.US
                speak(pendingText)
                pendingText = null
            }
        }
    }

    fun setMusicOn(on: Boolean) {
        isMusicOn = on
    }

    fun speak(text: String?) {
        if (text == null) return
        if (!isMusicOn || tts == null) return
        if (!isInitialized) {
            pendingText = text
            return
        }
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    fun stop() {
        tts?.stop()
    }

    fun release() {
        tts?.stop()
        tts?.shutdown()
        tts = null
    }
}
