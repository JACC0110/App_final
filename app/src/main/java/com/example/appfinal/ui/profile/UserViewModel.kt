package com.example.appfinal.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.appfinal.data.model.User
import com.example.appfinal.data.network.ApiClient
import com.example.appfinal.data.repository.UserRepository
import com.example.appfinal.utils.SessionManager
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository = UserRepository(application)
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchLoggedUser() {
        val userId = SessionManager.getUserId()
        if (userId != -1) {
            viewModelScope.launch {
                try {
                    val response = ApiClient.service.getUserById(userId)
                    if (response.isSuccessful && response.body() != null) {
                        _user.value = response.body()
                    } else {
                        _error.value = "Error al obtener datos del usuario"
                    }
                } catch (e: Exception) {
                    _error.value = "Error de red: ${e.message}"
                }
            }
        } else {
            _error.value = "No hay usuario autenticado"
        }
    }

    fun changePassword(userId: Int, newPassword: String, callback: (Boolean, String) -> Unit) {
        userRepository.changePassword(userId, newPassword, callback)
    }
}

