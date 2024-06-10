package com.example.latihanfirebase

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class PembayaranTiket : AppCompatActivity() {

    private lateinit var tvRoute: TextView
    private lateinit var tvOrderNumber: TextView
    private lateinit var tvPaymentDate: TextView
    private lateinit var tvPrice: TextView
    private lateinit var tvUniqueCode: TextView
    private lateinit var tvTotalPayment: TextView
    private lateinit var tvTicketRoute: TextView
    private lateinit var tvTicketCode: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pembayaran_tiket)

        tvRoute = findViewById(R.id.tv_route)
        tvOrderNumber = findViewById(R.id.tv_order_number)
        tvPaymentDate = findViewById(R.id.tv_payment_date)
        tvPrice = findViewById(R.id.tv_price)
        tvUniqueCode = findViewById(R.id.tv_unique_code)
        tvTotalPayment = findViewById(R.id.tv_total_payment)
        tvTicketRoute = findViewById(R.id.tv_ticket_route)
        tvTicketCode = findViewById(R.id.tv_ticket_code)

        // Get ticket ID from intent
        val ticketId = intent.getStringExtra("ticketId")

        // Initialize Firebase Database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("LatihanFireBase")

        if (ticketId != null) {
            myRef.child(ticketId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Assuming ticketData is structured as per the data we store
                    val ticketData = dataSnapshot.value as? Map<String, String>
                    if (ticketData != null) {
                        tvRoute.text = "${ticketData["from"]} -> ${ticketData["to"]}"
                        tvOrderNumber.text = "No Pesanan: $ticketId"
                        tvPaymentDate.text = "Tanggal Pembayaran: ${ticketData["date"]}"
                        tvPrice.text = "Tarif ${ticketData["from"]} -> ${ticketData["to"]} (x${ticketData["passengers"]}): Rp 120.000"
                        tvUniqueCode.text = "Kode Unik: Rp 529"
                        tvTotalPayment.text = "Total Pembayaran: Rp 120.529"
                        tvTicketRoute.text = "${ticketData["from"]} -> ${ticketData["to"]}"
                        tvTicketCode.text = "ORDER: $ticketId"
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle database error
                }
            })
        }
    }
}
