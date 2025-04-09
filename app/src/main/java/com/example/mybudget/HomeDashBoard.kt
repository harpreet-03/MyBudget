package com.example.mybudget

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeDashBoard : AppCompatActivity() {

    private lateinit var dbHelper: ExpenseDatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExpenseAdapter
    private lateinit var totalExpenseTextView: TextView

    override fun onResume() {
        super.onResume()
        loadExpenses()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_dash_board)

        dbHelper = ExpenseDatabaseHelper(this)
        recyclerView = findViewById(R.id.transactionsRecyclerView)
        totalExpenseTextView = findViewById(R.id.expenseInput)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = ExpenseAdapter()
        recyclerView.adapter = adapter

        val addBtn = findViewById<FloatingActionButton>(R.id.fabAddExpense)
        addBtn.setOnClickListener {
            startActivity(Intent(this, AddExpense::class.java))
        }

        loadExpenses()
    }

    private fun loadExpenses() {
        val expenses = dbHelper.getAllExpenses()
        val total = dbHelper.getTotalExpense()
        totalExpenseTextView.text = "₹$total"
        adapter.setData(expenses)
    }
}
