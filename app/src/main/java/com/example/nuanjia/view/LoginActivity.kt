package com.example.nuanjia.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.example.nuanjia.R
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //隐藏标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login)

        //登录
        login_btn.setOnClickListener {
            if (doValidateForm()) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    //校验用户字段
    private fun doValidateForm(): Boolean {
        var user=user_edt.text.toString();
        var pwd=password_edt.text.toString();
        if (user.isEmpty()) {
            Toast.makeText(this,"用户名不能为空！",LENGTH_SHORT).show()
            return false
        } else if (pwd.isEmpty()) {
            Toast.makeText(this,"密码不能为空！",LENGTH_SHORT).show()
            return false
        }
        return true
    }

}

