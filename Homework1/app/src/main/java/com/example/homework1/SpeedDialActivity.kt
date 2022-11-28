package com.example.homework1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class SpeedDialActivity : AppCompatActivity() {
    private var dialId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speed_dial_manager)
        val dialIdKey = "DIAL_ID"
        val callingIntent = intent
        dialId = callingIntent.getIntExtra(dialIdKey, 0)
        val dialBeingEditedTextView = findViewById<TextView>(R.id.text_speed_dial_editting)
        dialBeingEditedTextView.text =
            String.format(Locale.ENGLISH, "Editing Dial %d", dialId)
    }

    fun onSaveClick(view: View?) {
        val editTextDialName = findViewById<EditText>(R.id.editText_dial_name)
        val editTextDialNumber = findViewById<EditText>(R.id.editText_dial_number)
        if (editTextDialName.text.isEmpty() || editTextDialNumber.text.isEmpty()) {
            Toast.makeText(applicationContext, "Invalid fields", Toast.LENGTH_SHORT).show()
            return
        }
        returnReply()
    }

    fun returnReply() {
        val editTextDialName = findViewById<EditText>(R.id.editText_dial_name)
        val editTextDialNumber = findViewById<EditText>(R.id.editText_dial_number)
        val dialName = editTextDialName.text.toString()
        val dialNumber = editTextDialNumber.text.toString()
        val replyIntent = Intent()
        replyIntent.putExtra("DIAL_ID", dialId)
        replyIntent.putExtra("DIAL_NAME", dialName)
        replyIntent.putExtra("DIAL_NUMBER", dialNumber)
        setResult(RESULT_OK, replyIntent)
        finish()
    }
}