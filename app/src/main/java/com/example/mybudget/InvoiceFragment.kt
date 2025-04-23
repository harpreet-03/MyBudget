package com.example.mybudget

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.airbnb.lottie.LottieAnimationView
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import java.io.File
import java.io.FileOutputStream

class Invoice : AppCompatActivity() {

    private lateinit var dbHelper: ExpenseDatabaseHelper
    private lateinit var pdfFile: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice)

        dbHelper = ExpenseDatabaseHelper(this)

        val animationView = findViewById<LottieAnimationView>(R.id.invoice)
        animationView.setAnimation(R.raw.invoice_motion)
        animationView.playAnimation()

        val generateBtn = findViewById<Button>(R.id.btnGenerateInvoice)
        generateBtn.setOnClickListener {
            generatePDFAndOpen()
        }
    }

    private fun generatePDFAndOpen() {
        val expenses = dbHelper.getAllExpenses()
        val document = Document()

        try {
            // ✅ Safe internal folder: /storage/emulated/0/Android/data/<package>/files/Documents
            val dir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            if (dir != null && !dir.exists()) dir.mkdirs()

            pdfFile = File(dir, "ExpenseInvoice.pdf")

            PdfWriter.getInstance(document, FileOutputStream(pdfFile))
            document.open()

            document.add(Paragraph("📜 MyBudget Expense Invoice\n\n"))

            val table = PdfPTable(2)
            table.addCell("Category")
            table.addCell("Amount (₹)")

            for (expense in expenses) {
                table.addCell(expense.category)
                table.addCell(expense.amount.toString())
            }

            document.add(table)
            Toast.makeText(this, "📄 Invoice PDF downloaded successfully!", Toast.LENGTH_SHORT).show()

            openPDF()

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "❌ Failed to generate PDF!", Toast.LENGTH_SHORT).show()
        } finally {
            document.close()
        }
    }

    private fun openPDF() {
        try {
            val uri: Uri = FileProvider.getUriForFile(
                this,
                "$packageName.fileprovider",
                pdfFile
            )

            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "application/pdf")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            startActivity(Intent.createChooser(intent, "Open PDF with"))
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "⚠️ No PDF viewer found!", Toast.LENGTH_LONG).show()
        }
    }
}