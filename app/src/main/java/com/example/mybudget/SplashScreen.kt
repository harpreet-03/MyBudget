package com.example.mybudget

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        // creating splash screen

        Handler().postDelayed({
            val intent = Intent(this, GetStarted::class.java)
            startActivity(intent)
            finish()
        }, 1500)
    }
}