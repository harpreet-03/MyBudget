package com.example.mybudget

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class HomeDashBoard : AppCompatActivity() {

    private lateinit var dbHelper: ExpenseDatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExpenseAdapter
    private lateinit var totalExpenseTextView: TextView
    private var lastDeletedExpense: Expense? = null

    // Refresh screen when returning to the dashboard
    override fun onResume() {
        super.onResume()
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.selectedItemId = R.id.nav_home
        loadExpenses()
    }

    // Initialize the dashboard screen and set up components
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_dash_board)

        // Load saved username from SharedPreferences
        val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val userName = prefs.getString("username", "User") // default fallback name
        val nameTextView = findViewById<TextView>(R.id.UserName)
        nameTextView.text = userName

        // Set up database, recycler view, and adapter
        dbHelper = ExpenseDatabaseHelper(this)
        recyclerView = findViewById(R.id.transactionsRecyclerView)
        totalExpenseTextView = findViewById(R.id.expenseInput)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = ExpenseAdapter { expense ->
            deleteWithUndo(expense)
        }
        recyclerView.adapter = adapter

        // Handle FAB click to add a new expense
        findViewById<FloatingActionButton>(R.id.fabAddExpense).setOnClickListener {
            startActivity(Intent(this, AddExpense::class.java))
        }

        // Load expenses initially
        loadExpenses()

        // Set click listener for notification icon
        val notify = findViewById<ImageView>(R.id.notificationIcon)
        notify.setOnClickListener {
            Toast.makeText(this, "No notification yet ✌🏻", Toast.LENGTH_SHORT).show()
        }

        // Setup bottom navigation bar
        setupBottomNavigation()
    }

    // Configure bottom navigation and handle item clicks
    private fun setupBottomNavigation() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.selectedItemId = R.id.nav_home // default selected

        bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    // Already on Home, do nothing
                    true
                }

                R.id.nav_invoice -> {
                    // Navigate to Invoice screen
                    val intent = Intent(this, Invoice::class.java)
                    startActivity(intent)
                    true
                }

                R.id.nav_stats -> {
                    // Show toast for Stats (upcoming feature)
                    Toast.makeText(this, "Stats Updating soon!", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.nav_profile -> {
                    // Open Profile Bottom Sheet
                    val bottomSheet = ProfileBottomSheetFragment()
                    bottomSheet.show(supportFragmentManager, bottomSheet.tag)
                    true
                }

                else -> false
            }
        }
    }

    // Load expenses from database and update UI
    private fun loadExpenses() {
        val expenses = dbHelper.getAllExpenses()
        val total = dbHelper.getTotalExpense()

        // Set total expense amount
        totalExpenseTextView.text = "₹$total"

        // Update the RecyclerView with new data
        adapter.setData(expenses)
    }

    // Delete an expense with option to Undo via Snackbar
    private fun deleteWithUndo(expense: Expense) {
        lastDeletedExpense = expense

        // Delete expense from database
        dbHelper.deleteExpense(expense.category, expense.amount)

        // Reload updated expense list
        loadExpenses()

        // Show Snackbar with Undo action
        Snackbar.make(recyclerView, "Deleted: ${expense.category}", Snackbar.LENGTH_LONG)
            .setAction("UNDO") {
                // Re-add expense if Undo clicked
                lastDeletedExpense?.let {
                    dbHelper.addExpense(it)
                    loadExpenses()
                }
            }.show()
    }
}
