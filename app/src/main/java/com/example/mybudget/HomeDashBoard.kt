package com.example.mybudget

import ExpenseDatabaseHelper
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class HomeDashBoard : AppCompatActivity() {

    private lateinit var expenseInput: EditText
    private lateinit var totalExpenseTextView: TextView
    private lateinit var dbHelper: ExpenseDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_dash_board)

        val notificationIcon = findViewById<ImageView>(R.id.notificationIcon)
        notificationIcon.setOnClickListener {
            Toast.makeText(this, "Notifications clicked", Toast.LENGTH_SHORT).show()
        }

        expenseInput = findViewById(R.id.expenseInput)
        totalExpenseTextView = findViewById(R.id.textView2)

        dbHelper = ExpenseDatabaseHelper(this)
        updateTotalExpense()

        expenseInput.setOnEditorActionListener { _, _, _ ->
            val input = expenseInput.text.toString().trim()

            if (input.isNotEmpty()) {
                val expense = input.toDoubleOrNull()
                if (expense != null) {
                    dbHelper.insertExpense(expense)
                    updateTotalExpense()
                    expenseInput.text.clear()
                    Snackbar.make(expenseInput, "Expense Added!", Snackbar.LENGTH_SHORT).show()
                } else {
                    Snackbar.make(expenseInput, "Invalid Input!", Snackbar.LENGTH_SHORT).show()
                }
            }
            true
        }
    }

    private fun updateTotalExpense() {
        val totalExpense = dbHelper.getTotalExpense()
        totalExpenseTextView.text = "Total Expense: ₹$totalExpense"
    }
}