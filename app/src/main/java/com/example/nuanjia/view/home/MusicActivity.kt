package com.example.nuanjia.view.home


import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.view.Window
import android.widget.SeekBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.nuanjia.R
import com.example.nuanjia.Tool
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_music.*
import kotlin.concurrent.thread

class MusicActivity : AppCompatActivity() ,ServiceConnection{

    val TAG = "MusicActivity"
    var binder:MusicService.MusicBinder?=null
    var flag = 0
    var current = 0
    val musicPhoto = mutableListOf<Int>(R.drawable.square_show,R.drawable.square_show1,R.drawable.square_show2)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        val tool = Tool()
        tool.setWindowStatusBarColor(this,R.color.green)
        setContentView(R.layout.activity_music)

        btn_back.setOnClickListener {
            finish()
        }

        photo.setImageResource(musicPhoto[current+2])


        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                0
            )
        } else {
            startMusicService()
        }


        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                   binder?.currentPosition= progress
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun startMusicService(){
        val intent=Intent(this,MusicService::class.java)
        intent.putExtra(MusicService.Commond,1)
        startService(intent)
        bindService(intent,this,Context.BIND_AUTO_CREATE)
    }

    fun onPlay(v: View) {
        val intent=Intent(this,MusicService::class.java)
        intent.putExtra(MusicService.Commond,1)
        startService(intent)
    }

    //播放/暂停切换
    fun onPause(v: View) {
        when(flag){
            0 -> {
                pause.isActivated = true
                flag = 1
            }
            1 -> {
                pause.isActivated = false
                flag = 0
            }
        }
        val intent=Intent(this,MusicService::class.java)
        intent.putExtra(MusicService.Commond,2)
        startService(intent)
    }

    fun onStop(v: View) {
        val intent=Intent(this,MusicService::class.java)
        intent.putExtra(MusicService.Commond,3)
        startService(intent)
    }

    fun onNext(v: View) {
        if (current >= musicPhoto.size) {
            current = 0
        }
        photo.setImageResource(musicPhoto[current++])
        val intent=Intent(this,MusicService::class.java)
        intent.putExtra(MusicService.Commond,4)
        startService(intent)
    }

    fun onPrev(v: View) {
        if (current < 0) {
            current = musicPhoto.size - 1
        }
        photo.setImageResource(musicPhoto[current++])

        val intent=Intent(this,MusicService::class.java)
        intent.putExtra(MusicService.Commond,5)
        startService(intent)
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }



    override fun onDestroy() {
        super.onDestroy()
        unbindService(this)

    }

    override fun onServiceDisconnected(name: ComponentName?) {
        binder = null
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
       binder = service as MusicService.MusicBinder
        thread {
            while (binder !=null) {
                Thread.sleep(500)
                runOnUiThread {
                    seekBar.max = binder!!.duration
                    seekBar.progress = binder!!.currentPosition
                    textView_name.text=binder!!.musicName
                    textView_count.text="${binder!!.currentIndex+1}/${binder?.size}"
                }
            }
        }
    }
}