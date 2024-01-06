package com.callastro.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import com.callastro.MainActivity
import com.callastro.R
import com.maxtra.callastro.prefs.UserPref

class SplashScreen : AppCompatActivity() {
    private  val splashTimeout : Long = 2000 //2sec
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        Handler().postDelayed({

            val userPref = UserPref(this)
            if(userPref.isLogin){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        },splashTimeout)

    }
}