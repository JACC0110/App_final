package com.example.appfinal.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.appfinal.R
import android.widget.Button
import com.example.appfinal.ui.login.Login
import com.example.appfinal.ui.register.Register

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnLogin.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }

        btnRegister.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }
    }
}
