package com.example.mybudget

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class HomeDashBoard : AppCompatActivity() {

    private lateinit var dbHelper: ExpenseDatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExpenseAdapter
    private lateinit var totalExpenseTextView: TextView
    private var lastDeletedExpense: Expense? = null

    override fun onResume() {
        super.onResume()
        loadExpenses()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_dash_board)

        val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val userName = prefs.getString("username", "User") // default fallback name
        val nameTextView = findViewById<TextView>(R.id.UserName)
        nameTextView.text = userName



        dbHelper = ExpenseDatabaseHelper(this)
        recyclerView = findViewById(R.id.transactionsRecyclerView)
        totalExpenseTextView = findViewById(R.id.expenseInput)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = ExpenseAdapter { expense ->
            deleteWithUndo(expense)
        }
        recyclerView.adapter = adapter

        findViewById<FloatingActionButton>(R.id.fabAddExpense).setOnClickListener {
            startActivity(Intent(this, AddExpense::class.java))
        }

        loadExpenses()


        // notification click
        val notify = findViewById<ImageView>(R.id.notificationIcon)
        notify.setOnClickListener {
            Toast.makeText(this, "No notification yet ✌🏻", Toast.LENGTH_SHORT).show()
        }

    }


    private fun loadExpenses() {
        val expenses = dbHelper.getAllExpenses()
        val total = dbHelper.getTotalExpense()

        totalExpenseTextView.text = "₹$total"
        adapter.setData(expenses)
    }

    private fun deleteWithUndo(expense: Expense) {
        lastDeletedExpense = expense
        dbHelper.deleteExpense(expense.category, expense.amount)
        loadExpenses()

        Snackbar.make(recyclerView, "Deleted: ${expense.category}", Snackbar.LENGTH_LONG)
            .setAction("UNDO") {
                lastDeletedExpense?.let {
                    dbHelper.addExpense(it)
                    loadExpenses()
                }
            }.show()
    }
}