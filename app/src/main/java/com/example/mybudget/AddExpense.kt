package com.example.mybudget

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddExpense : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        val etCategory = findViewById<EditText>(R.id.category)
        val etAmount = findViewById<EditText>(R.id.amountInput)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val leftIcon =  findViewById<ImageView>(R.id.leftIcon)

        leftIcon.setOnClickListener {
            val intent=Intent(this,HomeDashBoard::class.java)
            startActivity(intent)
        }

        val dbHelper = ExpenseDatabaseHelper(this)

        btnSave.setOnClickListener {
            val category = etCategory.text.toString()
            val amount = etAmount.text.toString().toIntOrNull() ?: 0
            dbHelper.insertExpense(category, amount)
            finish() // Go back to home activity
        }
    }
}
