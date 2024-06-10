package com.example.latihanfirebase

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import java.util.Calendar

class PemesananTiket : AppCompatActivity() {

    private lateinit var edt_time: AutoCompleteTextView
    private lateinit var edt_departure_date: AutoCompleteTextView
    private lateinit var edt_passenger: AutoCompleteTextView
    private lateinit var edt_from: AutoCompleteTextView
    private lateinit var edt_to: AutoCompleteTextView
    private lateinit var btn_jam: ImageButton
    private lateinit var btn_date: ImageButton
    private lateinit var btn_search: Button

    private val item = arrayOf("1", "2", "3", "4")
    private val cities = arrayOf(
        "Jakarta",
        "Bandung",
        "Surabaya",
        "Semarang",
        "Yogyakarta",
        "Malang",
        "Solo",
        "Tangerang"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pemesanan_tiket)

        // Initialize Firebase Database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("LatihanFireBase")

        edt_time = findViewById(R.id.edt_time)
        edt_departure_date = findViewById(R.id.edt_departure_date)
        edt_passenger = findViewById(R.id.edt_passengers)
        edt_from = findViewById(R.id.edt_from)
        edt_to = findViewById(R.id.edt_to)
        btn_jam = findViewById(R.id.btn_jam)
        btn_date = findViewById(R.id.btn_date)
        btn_search = findViewById(R.id.btn_search)

        btn_jam.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(
                this,
                { _, selectedHour, selectedMinute ->
                    val amPm = if (selectedHour >= 12) "PM" else "AM"
                    val hourIn12Format = if (selectedHour % 12 == 0) 12 else selectedHour % 12
                    edt_time.setText(
                        String.format(
                            "%02d:%02d %s",
                            hourIn12Format,
                            selectedMinute,
                            amPm
                        )
                    )
                },
                hour,
                minute,
                false // use 12-hour format
            )
            timePickerDialog.show()
        }

        btn_date.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    edt_departure_date.setText(
                        String.format(
                            "%02d/%02d/%04d",
                            selectedDay,
                            selectedMonth + 1,
                            selectedYear
                        )
                    )
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

        val passengerAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, item)
        edt_passenger.setAdapter(passengerAdapter)

        val cityAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, cities)
        edt_from.setAdapter(cityAdapter)
        edt_to.setAdapter(cityAdapter)

        btn_search.setOnClickListener {
            val ticketDetails = """
                From: ${edt_from.text}
                To: ${edt_to.text}
                Time: ${edt_time.text}
                Date: ${edt_departure_date.text}
                Passengers: ${edt_passenger.text}
            """.trimIndent()

            // Create a unique ID for each ticket order
            val ticketId = myRef.push().key ?: "ticket_${System.currentTimeMillis()}"

            // Create a data map to store in Firebase
            val ticketData = mapOf(
                "date" to edt_departure_date.text.toString(),
                "from" to edt_from.text.toString(),
                "to" to edt_to.text.toString(),
                "passengers" to edt_passenger.text.toString(),
                "time" to edt_time.text.toString()
            )

            // Save the data to Firebase Realtime Database
            myRef.child(ticketId).setValue(ticketData)

            val intent = Intent(this, PembayaranTiket::class.java)
            intent.putExtra("ticketId", ticketId)
            startActivity(intent)
        }
    }
}
