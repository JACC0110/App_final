package com.example.appfinal.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appfinal.R
import com.example.appfinal.data.repository.UserRepository
import com.example.appfinal.ui.avatar.SelectAvatarActivity
import com.example.appfinal.ui.destino.DestinoListActivity
import com.example.appfinal.ui.register.Register
import com.example.appfinal.utils.SessionManager
class Login : AppCompatActivity() {
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SessionManager.init(applicationContext)
        setContentView(R.layout.activity_login)
        userRepository = UserRepository(this)

        val user = findViewById<EditText>(R.id.user)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val register = findViewById<Button>(R.id.btnGoRegister)

        login.setOnClickListener {
            val valUser = user.text.toString().trim()
            val valPassword = password.text.toString().trim()

            if (valUser.isBlank() || valPassword.isBlank()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            userRepository.login(valUser, valPassword) { success, message, user ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                if (success && user != null) {
                    SessionManager.saveUserId(user.id)
                    val next = if (user.first_login == true)
                        Intent(this, SelectAvatarActivity::class.java)
                    else
                        Intent(this, DestinoListActivity::class.java)
                    startActivity(next)
                    finish()
                }
            }
        }

        register.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }
    }
}


