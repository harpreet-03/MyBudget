package com.example.mybudget

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AddExpense : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        val categorySpinner = findViewById<Spinner>(R.id.categorySpinner)
        val etAmount = findViewById<EditText>(R.id.amountInput)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val leftIcon = findViewById<ImageView>(R.id.leftIcon)

        // Go back to home screen
        leftIcon.setOnClickListener {
            val intent = Intent(this, HomeDashBoard::class.java)
            startActivity(intent)
            finish()
        }

        // Set up category options in the spinner
        val categories = arrayOf("Food", "Travel", "Shopping", "Bills", "Entertainment", "Health", "Others")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories)
        categorySpinner.adapter = adapter

        val dbHelper = ExpenseDatabaseHelper(this)

        btnSave.setOnClickListener {
            val category = categorySpinner.selectedItem.toString()
            val amountText = etAmount.text.toString().trim()

            if (amountText.isEmpty()) {
                Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show()
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