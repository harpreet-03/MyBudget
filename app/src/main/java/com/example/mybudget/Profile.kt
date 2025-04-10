package com.example.mybudget

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class Profile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val listView = findViewById<ListView>(R.id.list)

        val options = listOf(
            ListItem("Invite Friends", R.drawable.baseline_diamond_24),
            ListItem("Account Info", R.drawable.baseline_person_24),
            ListItem("Personal Profile", R.drawable.baseline_people_alt_24),
            ListItem("Message Center", R.drawable.baseline_mail_24),
            ListItem("Login and Security", R.drawable.baseline_security_24),
            ListItem("Data and Privacy", R.drawable.baseline_lock_24)
        )

        val adapter = CustomAdapter(this, options)
        listView.adapter = adapter

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)

// Set selected item
        bottomNavigation.selectedItemId = R.id.nav_profile

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, HomeDashBoard::class.java))
                    overridePendingTransition(0, 0)
                    true
                }
                R.id.nav_profile -> {
                    // Already on profile
                    true
                }
                else -> false
            }
        }

    }
}
