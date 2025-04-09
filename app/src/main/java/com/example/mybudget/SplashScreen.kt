package com.example.mybudget

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        // creating splash screen

        Handler(Looper.getMainLooper()).postDelayed({
            val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
            val userName = prefs.getString("username", null)

            if (userName != null) {
                // Name already saved, go to HomeDashboard
                startActivity(Intent(this, HomeDashBoard::class.java))
            } else {
                // No name saved, go to GetStartedActivity
                startActivity(Intent(this, GetStarted::class.java))
            }

            finish()
        }, 1000)
    }
}