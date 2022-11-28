package com.example.homework1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private var dialerString: String? = null
    private var speedDials: ArrayList<SpeedDial>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dialerString = ""
        speedDials = arrayListOf(SpeedDial(), SpeedDial(), SpeedDial())

        setupDeleteBtnLongPress()
        setupSpeedDialLongPress()
    }

    fun addNumber(view: View) {
        val clickedButton = view as Button
        val buttonText = clickedButton.text.toString()
        val regex = """[0-9]+""".toRegex()
        if (buttonText.matches(regex)) {
            dialerString += buttonText
        }
        onDialStringChange()
    }

    fun deleteNumber(view: View?) {
        if (dialerString!!.isEmpty()) return
        dialerString = dialerString!!.substring(0, dialerString!!.length - 1)
        onDialStringChange()
    }

    fun callNumber(view: View?) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse(String.format("tel:%s", dialerString))
        startActivity(intent)
    }

    fun speedDial(view: View) {
        val intent = Intent(Intent.ACTION_DIAL)
        if (view.id == R.id.button_dial_1) {
            intent.data = Uri.parse(java.lang.String.format("tel:%s", speedDials!![0].number))
        } else if (view.id == R.id.button_dial_2) {
            intent.data = Uri.parse(java.lang.String.format("tel:%s", speedDials!![1].number))
        } else if (view.id == R.id.button_dial_3) {
            intent.data = Uri.parse(java.lang.String.format("tel:%s", speedDials!![2].number))
        }
        startActivity(intent)
    }

    private fun onDialStringChange() {
        val textView = findViewById<TextView>(R.id.textView)
        textView.text = dialerString
    }

    private fun onSpeedDialChange() {
        val speedDial1Button = findViewById<Button>(R.id.button_dial_1)
        val speedDial2Button = findViewById<Button>(R.id.button_dial_2)
        val speedDial3Button = findViewById<Button>(R.id.button_dial_3)
        speedDial1Button.text = speedDials!![0].name
        speedDial2Button.text = speedDials!![1].name
        speedDial3Button.text = speedDials!![2].name
    }

    private fun setupDeleteBtnLongPress() {
        val deleteBtn = findViewById<ImageButton>(R.id.button_delete)
        deleteBtn.setOnLongClickListener {
            dialerString = ""
            onDialStringChange()
            true
        }
    }

    private fun setupSpeedDialLongPress() {
        val dialIdKey = "DIAL_ID"
        val buttonDial1 = findViewById<Button>(R.id.button_dial_1)
        val buttonDial2 = findViewById<Button>(R.id.button_dial_2)
        val buttonDial3 = findViewById<Button>(R.id.button_dial_3)
        val launchSpeedDialWithResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data ?: return@registerForActivityResult
                val dialId = data.getIntExtra("DIAL_ID", 1) - 1
                val dialName = data.getStringExtra("DIAL_NAME")
                val dialNumber = data.getStringExtra("DIAL_NUMBER")
                speedDials?.set(dialId, SpeedDial(dialName!!, dialNumber!!))
                onSpeedDialChange()
            }
        }
        buttonDial1.setOnLongClickListener {
            val intent = Intent(this, SpeedDialActivity::class.java)
            intent.putExtra(dialIdKey, 1)
            launchSpeedDialWithResult.launch(intent)
            true
        }
        buttonDial2.setOnLongClickListener {
            val intent = Intent(this, SpeedDialActivity::class.java)
            intent.putExtra(dialIdKey, 2)
            launchSpeedDialWithResult.launch(intent)
            true
        }
        buttonDial3.setOnLongClickListener {
            val intent = Intent(this, SpeedDialActivity::class.java)
            intent.putExtra(dialIdKey, 3)
            launchSpeedDialWithResult.launch(intent)
            true
        }
    }
}