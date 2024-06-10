package com.example.latihanfirebase

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Set layout ke activity_splash

        // Menunggu beberapa detik sebelum melanjutkan ke ActivityLogin
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, ActivityLogin::class.java)
            startActivity(intent)
            finish()
        }, 3000) // Menunggu selama 3 detik
    }
}
