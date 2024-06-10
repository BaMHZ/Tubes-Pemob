// ActivityHome.kt
package com.example.latihanfirebase

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class ActivityHome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Find the ImageView by its ID
        val shuttleIcon: ImageView= findViewById(R.id.shuttle)

        // Set an OnClickListener on the ImageView
        shuttleIcon.setOnClickListener {
            // Create an Intent to start the PemesananTiket activity
            val intent = Intent(this, PemesananTiket::class.java)
            startActivity(intent)
        }
    }
}
