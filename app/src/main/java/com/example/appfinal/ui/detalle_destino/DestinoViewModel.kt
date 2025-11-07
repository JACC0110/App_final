package com.example.appfinal.ui.detalle_destino

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.appfinal.data.model.Destino
import com.example.appfinal.data.repository.DestinoRepository

class DestinoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = DestinoRepository(application)

    private val _destino = MutableLiveData<Destino?>()
    val destino: LiveData<Destino?> get() = _destino

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchDestinoById(id: Int) {
        repository.getDestinoById(id) { success, message, data ->
            if (success && data != null) {
                _destino.postValue(data)
            } else {
                _error.postValue(message)
            }
        }
    }
}

