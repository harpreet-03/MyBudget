import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ExpenseDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "MyBudget.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "expenses"
        const val COLUMN_ID = "id"
        const val COLUMN_AMOUNT = "amount"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = ("CREATE TABLE $TABLE_NAME ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_AMOUNT REAL)")
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Insert data
    fun insertExpense(amount: Double): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_AMOUNT, amount)

        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        return result
    }

    // Retrieve total expense amount
    fun getTotalExpense(): Double {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT SUM($COLUMN_AMOUNT) FROM $TABLE_NAME", null)
        var total = 0.0
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0)
        }
        cursor.close()
        return total
    }

    // Delete all expenses
    fun deleteAllExpenses() {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM $TABLE_NAME")
        db.close()
    }
}