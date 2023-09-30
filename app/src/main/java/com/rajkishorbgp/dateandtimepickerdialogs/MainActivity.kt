package com.rajkishorbgp.dateandtimepickerdialogs

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class MainActivity : AppCompatActivity() {

    private lateinit var editTextDate: EditText
    private lateinit var editTextTime: EditText
    private lateinit var button: Button
    private val calendar = Calendar.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextDate = findViewById(R.id.editTextDate)
        editTextTime = findViewById(R.id.editTextTime)
        button = findViewById(R.id.saveButton)

        editTextDate.setOnClickListener {
            hideKeyboard(editTextDate)
            showDatePicker()

        }
        editTextTime.setOnClickListener {
            hideKeyboard(editTextDate)
            showTimePickerDialog()
        }

        button.setOnClickListener {
            Toast.makeText(this@MainActivity,"Save",Toast.LENGTH_SHORT).show()
        }
    }

    private fun showTimePickerDialog() {
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this@MainActivity,
            { _, selectedHourOfDay, selectedMinute ->
                calendar.set(Calendar.HOUR_OF_DAY, selectedHourOfDay)
                calendar.set(Calendar.MINUTE, selectedMinute)

                val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault()) //HH:mm
                val formattedTime = timeFormat.format(calendar.time)
                editTextTime.setText(formattedTime)
            },
            hourOfDay,
            minute,
            false // Set to true to use the 24-hour format
        )
        timePickerDialog.show()
    }
    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = "${dayOfMonth.toString().padStart(2, '0')}-${(monthOfYear + 1).toString().padStart(2, '0')}-$year"
                editTextDate.setText(selectedDate)
            },
            // Set initial date in DatePickerDialog (optional)
            2023,  // Initial year
            0,     // Initial month (0 is January, 11 is December)
            1      // Initial day
        )
        // Show the DatePickerDialog
        datePickerDialog.show()
    }
    private fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
