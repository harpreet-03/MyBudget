package com.example.mybudget

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddExpense : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        val etCategory = findViewById<EditText>(R.id.category)
        val etAmount = findViewById<EditText>(R.id.amountInput)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val leftIcon = findViewById<ImageView>(R.id.leftIcon)

        leftIcon.setOnClickListener {
            val intent = Intent(this, HomeDashBoard::class.java)
            startActivity(intent)
            finish() // Optional: close AddExpense so it doesn't stay in the backstack
        }

        val dbHelper = ExpenseDatabaseHelper(this)

        btnSave.setOnClickListener {
            val category = etCategory.text.toString().trim()
            val amountText = etAmount.text.toString().trim()

            if (category.isEmpty() || amountText.isEmpty()) {
                Toast.makeText(this, "Please enter both category and amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val amount = amountText.toFloatOrNull()
            if (amount == null || amount <= 0) {
                Toast.makeText(this, "Please enter a valid positive amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val success = dbHelper.insertExpense(category, amount)
            if (success) {
                Toast.makeText(this, "Expense added successfully!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed to add expense", Toast.LENGTH_SHORT).show()
            }
        }
    }
}