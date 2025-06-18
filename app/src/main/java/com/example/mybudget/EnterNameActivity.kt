package com.example.mybudget

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast // Import Toast for user feedback
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
            val name = nameInput.text.toString().trim() // Get the trimmed name

            // Check if the name is not empty
            if (name.isNotEmpty()) {
                val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
                prefs.edit().putString("username", name).apply()

                startActivity(Intent(this, HomeDashBoard::class.java))
                finish() // Finish this activity so the user can't go back to it with the back button
            } else {
                // Optionally, show an error message to the user
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
                // Or you could set an error on the EditText itself
                // nameInput.error = "Name cannot be empty"
            }
        }
    }
}