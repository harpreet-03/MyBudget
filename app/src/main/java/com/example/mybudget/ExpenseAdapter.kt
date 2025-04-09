package com.example.mybudget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpenseAdapter : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    private var expenses: List<Expense> = listOf()

    fun setData(newExpenses: List<Expense>) {
        expenses = newExpenses
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenses[position]
        holder.tvCategory.text = expense.category
        holder.tvAmount.text = "₹${expense.amount}"
    }

    override fun getItemCount(): Int = expenses.size

    class ExpenseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCategory: TextView = view.findViewById(R.id.tvCategory)
        val tvAmount: TextView = view.findViewById(R.id.tvAmount)
    }
}
