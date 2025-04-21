package com.example.mybudget

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ExpenseDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "expenses.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_NAME = "expenses"
        const val COLUMN_ID = "id"
        const val COLUMN_CATEGORY = "category"
        const val COLUMN_AMOUNT = "amount"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_CATEGORY TEXT,
                $COLUMN_AMOUNT INTEGER
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertExpense(category: String, amount: Float): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_CATEGORY, category)
            put(COLUMN_AMOUNT, amount)
        }
        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        return result != -1L
    }

    fun getAllExpenses(): List<Expense> {
        val expenses = mutableListOf<Expense>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_ID DESC", null)
        if (cursor.moveToFirst()) {
            do {
                val category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY))
                val amount = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT))
                expenses.add(Expense(category, amount))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return expenses
    }

    fun getTotalExpense(): Int {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT SUM($COLUMN_AMOUNT) as total FROM $TABLE_NAME", null)
        var total = 0
        if (cursor.moveToFirst()) {
            total = cursor.getInt(cursor.getColumnIndexOrThrow("total"))
        }
        cursor.close()
        return total
    }

    fun deleteExpense(category: String, amount: Int): Boolean {
        val db = writableDatabase
        return db.delete(
            TABLE_NAME,
            "$COLUMN_CATEGORY = ? AND $COLUMN_AMOUNT = ?",
            arrayOf(category, amount.toString())
        ) > 0
    }

    fun addExpense(expense: Expense): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_CATEGORY, expense.category)
            put(COLUMN_AMOUNT, expense.amount)
        }
        return db.insert(TABLE_NAME, null, values) != -1L
    }
}