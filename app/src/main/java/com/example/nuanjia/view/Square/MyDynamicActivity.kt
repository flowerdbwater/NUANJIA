package com.example.nuanjia.view.Square

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nuanjia.R
import com.example.nuanjia.SpacesItemDecoration
import com.example.nuanjia.Tool
import com.example.nuanjia.view.MyDynamicAdapter
import kotlinx.android.synthetic.main.activity_my_dynamic.*
import kotlinx.android.synthetic.main.activity_my_dynamic.recyclerview
import kotlinx.android.synthetic.main.fragment_square.*

class MyDynamicActivity : AppCompatActivity() {

    var list: ArrayList<HashMap<String, Any>> = ArrayList<HashMap<String, Any>>()
    var map: HashMap<String, Any> = HashMap<String, Any>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        val tool:Tool = Tool()
        tool.setWindowStatusBarColor(this,R.color.green)
        setContentView(R.layout.activity_my_dynamic)

        map["profile_photo"] = R.drawable.profile
        map["name"] = "花水水"
        map["time"] = "5:20"
        map["tvContent"] = "welcome!!!汪苏泷汪苏泷，苏音唯美，爱泷不悔！！！汪苏泷汪苏泷，苏音唯美，爱泷不悔！！！"
        map["ivContent"] = R.drawable.square_show
        list.add(map)

        map["profile_photo"] = R.drawable.profile
        map["name"] = "花水水"
        map["time"] = "5:20"
        map["tvContent"] = "welcome!!!汪苏泷汪苏泷，苏音唯美，爱泷不悔！！！汪苏泷汪苏泷，苏音唯美，爱泷不悔！！！"
        map["ivContent"] = R.drawable.square_show
        list.add(map)

        map["profile_photo"] = R.drawable.profile
        map["name"] = "花水水"
        map["time"] = "5:20"
        map["tvContent"] = "welcome!!!汪苏泷汪苏泷，苏音唯美，爱泷不悔！！！汪苏泷汪苏泷，苏音唯美，爱泷不悔！！！"
        map["ivContent"] = R.drawable.square_show
        list.add(map)

        map["profile_photo"] = R.drawable.profile
        map["name"] = "花水水"
        map["time"] = "5:20"
        map["tvContent"] = "welcome!!!汪苏泷汪苏泷，苏音唯美，爱泷不悔！！！汪苏泷汪苏泷，苏音唯美，爱泷不悔！！！"
        map["ivContent"] = R.drawable.square_show
        list.add(map)


        val manager = LinearLayoutManager(this)
        recyclerview.layoutManager = manager

        //给动态之间添加间隔
        val space = 25
        val spacesItemDecoration = SpacesItemDecoration(space)
        recyclerview.addItemDecoration(spacesItemDecoration)

        val adapter = MyDynamicAdapter(list)
        if (recyclerview != null){
            recyclerview.adapter = adapter
        }

        btn_back.setOnClickListener {
            finish()
        }

        btn_add.setOnClickListener {
            val intent = Intent(this,PublishActivity::class.java)
            startActivity(intent)
        }
    }

}