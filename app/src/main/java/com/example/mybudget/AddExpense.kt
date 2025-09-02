package com.example.mybudget

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AddExpense : AppCompatActivity() {

    private lateinit var categorySpinner: Spinner
    private lateinit var customCategoryText: TextView
    private lateinit var adapter: ArrayAdapter<String>
    private val categoryList = arrayListOf("Food", "Travel", "Shopping", "Bills", "Entertainment", "Health", "Grocery", "Education", "Others")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        categorySpinner = findViewById(R.id.categorySpinner)
        customCategoryText = findViewById(R.id.customCategoryText)
        val etAmount = findViewById<EditText>(R.id.amountInput)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val leftIcon = findViewById<ImageView>(R.id.leftIcon)

        // Go back to home screen
        leftIcon.setOnClickListener {
            val intent = Intent(this, HomeDashBoard::class.java)
            startActivity(intent)
            finish()
        }

        adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categoryList)
        categorySpinner.adapter = adapter

        customCategoryText.setOnClickListener {
            showCustomCategoryDialog()
        }

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

    private fun showCustomCategoryDialog() {
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setTitle("Add Custom Category")

        val input = EditText(this)
        input.hint = "Enter category"
        builder.setView(input)

        builder.setPositiveButton("Add") { dialog, _ ->
            val newCategory = input.text.toString().trim()
            if (newCategory.isNotEmpty()) {
                categoryList.add(newCategory)
                adapter.notifyDataSetChanged()
                categorySpinner.setSelection(categoryList.size - 1)
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }
}