package com.example.mybudget

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.snackbar.Snackbar
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

        // Load Lottie Animation
        val animationView = findViewById<LottieAnimationView>(R.id.invoice)
        animationView.setAnimation(R.raw.invoice_motion)
        animationView.playAnimation()

        // Back Button
        val back = findViewById<ImageView>(R.id.left2)
        back.setOnClickListener {
            startActivity(Intent(this, HomeDashBoard::class.java))
        }

        // Generate PDF
        val generateBtn = findViewById<Button>(R.id.btnGenerateInvoice)
        generateBtn.setOnClickListener {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q && !checkPermission()) {
                requestPermission()
            } else {
                generatePDFAndOpen()
            }
        }
    }

    private fun generatePDFAndOpen() {
        val expenses = dbHelper.getAllExpenses()
        val document = Document()

        try {
            val dir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            if (dir != null && !dir.exists()) {
                dir.mkdirs()
            }

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
            Toast.makeText(this, "PDF saved to: ${pdfFile.absolutePath}", Toast.LENGTH_LONG).show()

            openPDF()

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Failed to generate PDF!", Toast.LENGTH_SHORT).show()
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
            Toast.makeText(this, "No PDF viewer found.", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkPermission(): Boolean {
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return permission == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            101
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            generatePDFAndOpen()
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }
}