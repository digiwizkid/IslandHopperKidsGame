package com.digiwizkid.islandhopper.data.repository

import android.content.Context
import android.media.ToneGenerator
import android.media.AudioManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

internal class SoundManager {

    private var toneGenerator: ToneGenerator? = null

    private fun ensureToneGenerator(): ToneGenerator {
        if (toneGenerator == null) {
            toneGenerator = ToneGenerator(AudioManager.STREAM_NOTIFICATION, 80)
        }
        return toneGenerator!!
    }
    private var isMusicOn = true

    fun setMusicOn(on: Boolean) {
        isMusicOn = on
    }

    fun isMusicOn(): Boolean = isMusicOn

    fun playCorrectSound() {
        if (!isMusicOn) return
        ensureToneGenerator().startTone(ToneGenerator.TONE_PROP_ACK, 200)
    }

    fun playWrongSound() {
        if (!isMusicOn) return
        ensureToneGenerator().startTone(ToneGenerator.TONE_PROP_NACK, 300)
    }

    fun playStreakSound() {
        if (!isMusicOn) return
        ensureToneGenerator().startTone(ToneGenerator.TONE_PROP_BEEP2, 400)
    }

    fun playGameOverSound() {
        if (!isMusicOn) return
        ensureToneGenerator().startTone(ToneGenerator.TONE_PROP_PROMPT, 500)
    }

    fun vibrate(context: Context) {
        val v = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val manager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            manager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(
                VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE)
            )
        } else {
            @Suppress("DEPRECATION")
            v.vibrate(50)
        }
    }

    fun release() {
        toneGenerator?.release()
        toneGenerator = null
    }
}
