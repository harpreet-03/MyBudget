package com.example.mybudget

import android.view.*
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpenseAdapter(
    private val onDeleteRequested: (Expense) -> Unit
) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

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

        // Long-press menu with delete option
        holder.itemView.setOnLongClickListener {
            showPopupMenu(holder.itemView, expense)
            true
        }
    }

    private fun showPopupMenu(view: View, expense: Expense) {
        val popup = PopupMenu(view.context, view)
        popup.inflate(R.menu.expense_item_menu)
        popup.setOnMenuItemClickListener {
            if (it.itemId == R.id.menu_delete) {
                onDeleteRequested(expense)
                true
            } else false
        }
        popup.show()
    }

    override fun getItemCount(): Int = expenses.size

    class ExpenseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCategory: TextView = view.findViewById(R.id.tvCategory)
        val tvAmount: TextView = view.findViewById(R.id.tvAmount)
    }
}