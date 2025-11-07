package com.example.appfinal.data.repository

import android.content.Context
import com.example.appfinal.data.network.ApiClient
import com.example.appfinal.data.model.Destino
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DestinoRepository(private val context: Context) {

    private val apiService = ApiClient.service

    fun getDestinos(callback: (Boolean, String, List<Destino>?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.getDestinos()
                if (response.isSuccessful && response.body() != null) {
                    callback(true, "Datos obtenidos correctamente", response.body())
                } else {
                    callback(false, "Error al obtener destinos (${response.code()})", null)
                }
            } catch (e: Exception) {
                callback(false, "Error de red: ${e.localizedMessage ?: e.message}", null)
            }
        }
    }

    fun getDestinoById(id: Int, callback: (Boolean, String, Destino?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.getDestinoById(id)
                if (response.isSuccessful && response.body() != null) {
                    callback(true, "Destino obtenido correctamente", response.body())
                } else {
                    callback(false, "Error al obtener destino (${response.code()})", null)
                }
            } catch (e: Exception) {
                callback(false, "Error de red: ${e.localizedMessage ?: e.message}", null)
            }
        }
    }
}



