package com.callastro.util

import android.content.Context
import android.media.MediaPlayer
import android.os.Vibrator
import com.callastro.R

object SoundUtil {
    private var mMediaPlayer: MediaPlayer? = null
    private var vib: Vibrator? = null

    fun playSound(context: Context) {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(context, R.raw.rington)
            vib = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vib!!.vibrate(1000)
            mMediaPlayer!!.isLooping = false
            mMediaPlayer!!.start()
        } else {
            mMediaPlayer!!.start()
        }
    }

    fun stopSound(context: Context) {
        mMediaPlayer?.apply {
            if (isPlaying) {
                stop()
            }
            release()
        }
        mMediaPlayer = null
    }

}






