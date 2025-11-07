package com.example.appfinal.ui.detalle_destino

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.appfinal.R
import com.example.appfinal.data.model.Destino
import com.example.appfinal.databinding.ActivityDestinoBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.widget.ImageButton
import android.widget.PopupMenu
import android.content.Intent
import com.example.appfinal.ui.profile.ProfileActivity
import com.example.appfinal.ui.main.MainActivity
import com.example.appfinal.utils.SessionManager

class DestinoActivity : AppCompatActivity(), OnMapReadyCallback {

    private val destinoViewModel: DestinoViewModel by viewModels()
    private lateinit var binding: ActivityDestinoBinding
    private lateinit var mapView: MapView
    private var googleMap: GoogleMap? = null
    private var destinoActual: Destino? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_destino)
        binding.lifecycleOwner = this

        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        val destinoId = intent.getIntExtra("id_destino", -1)
        if (destinoId == -1) {
            Toast.makeText(this, "Destino no válido", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        destinoViewModel.destino.observe(this) { destino ->
            destino?.let {
                binding.destino = it
                destinoActual = it

                val resenasTexto = it.resenas?.joinToString("\n\n") { resena ->
                    "⭐ ${resena.calificacion}/5\n${resena.comentario}"
                } ?: "Sin reseñas disponibles"
                binding.tvResenas.text = resenasTexto

                googleMap?.let { map -> mostrarUbicacionEnMapa(map, it) }
            }
        }

        destinoViewModel.error.observe(this) { error ->
            if (!error.isNullOrEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            }
        }

        destinoViewModel.fetchDestinoById(destinoId)

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

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        destinoActual?.let {
            mostrarUbicacionEnMapa(map, it)
        }
    }

    private fun mostrarUbicacionEnMapa(map: GoogleMap, destino: Destino) {
        val ubicacion = LatLng(destino.latitud, destino.longitud)
        map.clear()
        map.addMarker(MarkerOptions().position(ubicacion).title(destino.nombre))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 13f))
    }

    override fun onResume() { super.onResume(); mapView.onResume() }
    override fun onPause() { super.onPause(); mapView.onPause() }
    override fun onDestroy() { super.onDestroy(); mapView.onDestroy() }
    override fun onLowMemory() { super.onLowMemory(); mapView.onLowMemory() }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}


