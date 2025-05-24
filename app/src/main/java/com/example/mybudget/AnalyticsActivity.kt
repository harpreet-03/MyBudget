package com.example.mybudget

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class AnalyticsActivity : AppCompatActivity() {

    private lateinit var dbHelper: ExpenseDatabaseHelper
    private lateinit var totalSpendText: TextView
    private lateinit var listView: ListView
    private lateinit var pieChart: PieChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analytics)

        totalSpendText = findViewById(R.id.tv_total_spend)
        listView = findViewById(R.id.category_list_view)
        pieChart = findViewById(R.id.pie_chart)

        dbHelper = ExpenseDatabaseHelper(this)

        loadAnalytics()
    }

    private fun loadAnalytics() {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT ${ExpenseDatabaseHelper.COLUMN_CATEGORY}, SUM(${ExpenseDatabaseHelper.COLUMN_AMOUNT}) " +
                    "FROM ${ExpenseDatabaseHelper.TABLE_NAME} GROUP BY ${ExpenseDatabaseHelper.COLUMN_CATEGORY}", null
        )

        val listItems = ArrayList<String>()
        val pieEntries = ArrayList<PieEntry>()
        var totalSpend = 0f

        if (cursor.moveToFirst()) {
            do {
                val category = cursor.getString(0)
                val amount = cursor.getFloat(1)
                totalSpend += amount

                listItems.add("$category: ₹$amount")
                pieEntries.add(PieEntry(amount, category))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        totalSpendText.text = "Total Spend: ₹${totalSpend.toInt()}"
        listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)

        val pieDataSet = PieDataSet(pieEntries, "Category-wise Spending")
        pieDataSet.setColors(*ColorTemplate.MATERIAL_COLORS)
        pieDataSet.valueTextSize = 14f
        pieChart.data = PieData(pieDataSet)
        pieChart.description.isEnabled = false
        pieChart.centerText = "Spending"
        pieChart.animateY(1000)
        pieChart.invalidate()
    }
}