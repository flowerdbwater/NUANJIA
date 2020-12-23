package com.example.nuanjia.view.home

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.provider.MediaStore
import android.util.Log
import com.example.nuanjia.R
import kotlinx.android.synthetic.main.activity_music.*
import java.io.IOException

class MusicService : Service() {

    companion object{
        const val Commond="Operate"
    }
    private val TAG = "MusicService"

    val mediaPlayer = MediaPlayer()
    val musicList = mutableListOf<String>()
    val musicNameList = mutableListOf<String>()
    var current = 0
    var isPause = false
    private val binder =MusicBinder()

    inner class MusicBinder:Binder(){
        val musicName
                get() = musicNameList[current]
        var currentPosition
        get() = mediaPlayer.currentPosition
        set(value) = mediaPlayer.seekTo(value)

        val duration
            get() = mediaPlayer.duration

        val size
            get() = musicList.size

        val currentIndex
            get() = current
    }

    override fun onCreate() {
        super.onCreate()
        getMusicList()
        mediaPlayer.setOnPreparedListener {
            it.start()
        }
        mediaPlayer.setOnCompletionListener {
            current++
            if (current >= musicList.size) {
                current = 0
            }
            play()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.getIntExtra(Commond,1)?:1){
            1 -> play()
            2 -> pause()
            3 -> stop()
            4 -> next()
            5 -> prev()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun play() {
        if (musicList.size == 0) return
        val path = musicList[current]
        mediaPlayer.reset()
        try {
            mediaPlayer.setDataSource(path)
            mediaPlayer.prepareAsync()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    private fun pause() {
        isPause = if (isPause) {
            mediaPlayer.start()
            false
        } else {
            mediaPlayer.pause()
            true
        }
    }

    private fun stop() {
        mediaPlayer.stop()
        stopSelf()
    }

    fun next() {
        current++
        if (current >= musicList.size) {
            current = 0
        }
        play()
    }

    private fun prev() {
        current--
        if (current < 0) {
            current = musicList.size - 1
        }
        play()
    }


    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    private fun getMusicList() {
        val cursor = contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            null
        )
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val musicPatch =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                musicList.add(musicPatch)
                val musicName =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                musicNameList.add(musicName)
                Log.d(TAG, "getMusicList:$musicPatch name:$musicName")
            }
            cursor.close()
        }
    }
}
