package com.example.appfinal.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Destino(
    val id_destino: Int,
    val nombre: String,
    val descripcion: String,
    val imagen: String?,
    val latitud: Double,
    val longitud: Double,
    val puntuacion: Double?,
    val resenas: List<Resena>? = emptyList()
) : Parcelable

@Parcelize
data class Resena(
    val id_resena: Int,
    val comentario: String,
    val calificacion: Int
) : Parcelable

