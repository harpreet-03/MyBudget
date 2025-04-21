package com.example.mybudget

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView

class EnterNameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_name)

        val getStartedBtn = findViewById<Button>(R.id.btnGetStarted)
        val animationView = findViewById<LottieAnimationView>(R.id.lottieAnimationView)
        val nameInput = findViewById<EditText>(R.id.nameInput)

        animationView.setAnimation(R.raw.anime2)
        animationView.playAnimation()

        getStartedBtn.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
            prefs.edit().putString("username", name).apply()

            startActivity(Intent(this, HomeDashBoard::class.java))
            finish()
        }
    }
}