package com.example.mybudget

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsCompat.Type
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.util.*

class HomeDashBoard : AppCompatActivity() {

    private lateinit var dbHelper: ExpenseDatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExpenseAdapter
    private lateinit var totalExpenseTextView: TextView
    private lateinit var prefs: android.content.SharedPreferences
    private var lastDeletedExpense: Expense? = null
    private var speechRecognizer: SpeechRecognizer? = null
    private lateinit var micAnimation: LottieAnimationView
    private lateinit var micHint: TextView

    private val categoryKeywordMap = mapOf(
        "milk" to "Groceries", "rice" to "Groceries", "vegetables" to "Groceries", "grocery" to "Groceries",
        "uber" to "Transport", "bus" to "Transport", "train" to "Transport", "petrol" to "Transport",
        "snacks" to "Food", "pizza" to "Food", "restaurant" to "Food", "food" to "Food",
        "movie" to "Entertainment", "netflix" to "Entertainment",
        "shopping" to "Shopping", "clothes" to "Shopping",
        "book" to "Education", "tuition" to "Education", "fees" to "Education"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_dash_board)

        micAnimation = findViewById(R.id.micAnimation)
        micHint = findViewById(R.id.micHint)

        val voiceInputBtn: FloatingActionButton = findViewById(R.id.voiceInputBtn)
        voiceInputBtn.setOnClickListener {
            startListening()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bottomNavigation)) { view, insets ->
            val systemBars = insets.getInsets(Type.systemBars())
            view.setPadding(0, 0, 0, systemBars.bottom)
            WindowInsetsCompat.CONSUMED
        }

        prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
        dbHelper = ExpenseDatabaseHelper(this)

        recyclerView = findViewById(R.id.transactionRecyclerView)
        totalExpenseTextView = findViewById(R.id.totalExpenses)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ExpenseAdapter { deleteWithUndo(it) }
        recyclerView.adapter = adapter

        setupUsername()
        setupBudgetEdit()
        setupSearchBar()
        setupVoiceInput()
        setupBottomNavigation()
        loadExpenses()
    }

    override fun onResume() {
        super.onResume()
        findViewById<BottomNavigationView>(R.id.bottomNavigation).selectedItemId = R.id.nav_home
        loadExpenses()
    }

    private fun setupUsername() {
        val userName = prefs.getString("username", "User") ?: "User"
        findViewById<TextView>(R.id.username).text = "Hey $userName 👋"
    }

    private fun setupBudgetEdit() {
        val budgetText = findViewById<TextView>(R.id.budgetAmount)
        val plannedBudget = prefs.getInt("planned_budget", 0)
        budgetText.text = "Planned: ₹$plannedBudget"

        budgetText.setOnClickListener {
            val input = EditText(this).apply {
                inputType = InputType.TYPE_CLASS_NUMBER
                setText(plannedBudget.toString())
            }

            AlertDialog.Builder(this)
                .setTitle("Set Monthly Budget")
                .setView(input)
                .setPositiveButton("Save") { _, _ ->
                    input.text.toString().toIntOrNull()?.let {
                        prefs.edit().putInt("planned_budget", it).apply()
                        budgetText.text = "Planned: ₹$it"
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    private fun setupSearchBar() {
        val searchEditText = findViewById<EditText>(R.id.searchEditText)
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                adapter.filter(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setupVoiceInput() {
        val voiceBtn = findViewById<FloatingActionButton>(R.id.voiceInputBtn)

        if (!SpeechRecognizer.isRecognitionAvailable(this)) {
            Toast.makeText(this, "Speech recognition not available", Toast.LENGTH_SHORT).show()
            return
        }

        voiceBtn.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), 101)
            } else {
                startSpeechRecognition()
            }
        }
    }

    private fun startListening() {
        startSpeechRecognition()
    }

    private fun startSpeechRecognition() {
        micAnimation.visibility = View.VISIBLE
        micHint.visibility = View.VISIBLE

        speechRecognizer?.destroy()
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        }

        speechRecognizer?.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                micAnimation.playAnimation()
                micHint.text = "Listening..."
            }

            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}

            override fun onEndOfSpeech() {
                micAnimation.pauseAnimation()
                micHint.text = "Processing..."
            }

            override fun onError(error: Int) {
                micAnimation.cancelAnimation()
                micAnimation.visibility = View.GONE
                micHint.text = "Tap to speak"
                micHint.visibility = View.GONE
//                Toast.makeText(this@HomeDashBoard, "Error: $error", Toast.LENGTH_SHORT).show()
            }

            override fun onResults(results: Bundle?) {
                micAnimation.cancelAnimation()
                micAnimation.visibility = View.GONE
                micHint.visibility = View.GONE

                val spokenText = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.get(0) ?: ""
                processSpokenText(spokenText)
            }

            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })

        speechRecognizer?.startListening(intent)
    }

    private fun processSpokenText(spokenText: String) {
        val lowerText = spokenText.lowercase(Locale.getDefault())
        val amount = Regex("(\\d+)").find(lowerText)?.value?.toIntOrNull()

        val category = categoryKeywordMap.entries.firstOrNull {
            lowerText.contains(it.key)
        }?.value ?: "Miscellaneous"

        if (amount != null && category.isNotBlank()) {
            val expense = Expense(category, amount)
            dbHelper.addExpense(expense)
            loadExpenses()
            Toast.makeText(this, "Added ₹$amount on $category", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Try again. Could not extract amount or category.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupBottomNavigation() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_add -> {
                    startActivity(Intent(this, AddExpense::class.java))
                    true
                }
                R.id.nav_invoice -> {
                    startActivity(Intent(this, Invoice::class.java))
                    true
                }
                R.id.nav_stats -> {
                    startActivity(Intent(this, AnalyticsActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun loadExpenses() {
        val expenses = dbHelper.getAllExpenses()
        val total = dbHelper.getTotalExpense()
        totalExpenseTextView.text = "Spent: ₹$total"
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
            }
            .setActionTextColor(ContextCompat.getColor(this, R.color.colorAccent))
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer?.destroy()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startSpeechRecognition()
        } else {
            Toast.makeText(this, "Microphone permission denied", Toast.LENGTH_SHORT).show()
        }
    }
}
