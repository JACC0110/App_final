package com.example.appfinal.ui.register

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.appfinal.R
import com.example.appfinal.data.repository.UserRepository
import com.example.appfinal.ui.login.Login

class Register : AppCompatActivity() {

    private val userRepository = UserRepository(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val usuario = findViewById<EditText>(R.id.etUsuario)
        val nombre = findViewById<EditText>(R.id.etNombre)
        val apellido = findViewById<EditText>(R.id.etApellido)
        val edad = findViewById<EditText>(R.id.etEdad)
        val email = findViewById<EditText>(R.id.etEmail)
        val genero = findViewById<Spinner>(R.id.spGenero)
        val password = findViewById<EditText>(R.id.etPassword)
        val confirmPassword = findViewById<EditText>(R.id.etConfirmPassword)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)

        val generos = arrayOf("Masculino", "Femenino")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, generos)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genero.adapter = adapter

        btnRegistrar.setOnClickListener {
            val username = usuario.text.toString().trim()
            val names = nombre.text.toString().trim()
            val lastnames = apellido.text.toString().trim()
            val edadNumero = edad.text.toString().trim().toIntOrNull()
            val emailText = email.text.toString().trim()
            val pass = password.text.toString()
            val confirmPass = confirmPassword.text.toString()
            val selectedGender = genero.selectedItem?.toString() ?: ""

            if (username.isEmpty() || names.isEmpty() || lastnames.isEmpty() ||
                edadNumero == null || emailText.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass != confirmPass) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            userRepository.register(username, names, lastnames, edadNumero, selectedGender, pass, emailText) { success, message ->
                runOnUiThread {
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    if (success) {
                        startActivity(Intent(this, Login::class.java))
                        finish()
                    }
                }
            }
        }
    }
}

