package com.example.appfinal.ui.destino

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appfinal.R
import com.example.appfinal.data.repository.DestinoRepository
import com.example.appfinal.ui.main.MainActivity
import com.example.appfinal.ui.profile.ProfileActivity
import com.example.appfinal.utils.SessionManager

class DestinoListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DestinoAdapter
    private lateinit var destinoRepository: DestinoRepository

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destino_list)

        recyclerView = findViewById(R.id.recyclerDestinos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = DestinoAdapter(emptyList())
        recyclerView.adapter = adapter

        destinoRepository = DestinoRepository(this)

        destinoRepository.getDestinos { success, message, destinos ->
            runOnUiThread {
                if (success && destinos != null) {
                    adapter = DestinoAdapter(destinos)
                    recyclerView.adapter = adapter
                } else {
                    Toast.makeText(this, message ?: "Error al obtener destinos", Toast.LENGTH_SHORT).show()
                }
            }
        }
        val btnMenu: ImageButton = findViewById(R.id.btnMenu)
        btnMenu.setOnClickListener {
            val popup = PopupMenu(this, btnMenu)
            popup.menuInflater.inflate(R.menu.menu_opciones, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_profile -> {
                        startActivity(Intent(this, ProfileActivity::class.java))
                        true
                    }
                    R.id.menu_logout -> {
                        SessionManager.clearSession()
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }
}
