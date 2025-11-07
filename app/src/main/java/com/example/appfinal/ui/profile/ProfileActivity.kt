package com.example.appfinal.ui.profile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.appfinal.R
import com.example.appfinal.databinding.ActivityProfileBinding
import com.example.appfinal.ui.avatar.SelectAvatarActivity
import com.example.appfinal.ui.password.ChangePasswordActivity
import com.example.appfinal.utils.SessionManager

class ProfileActivity : AppCompatActivity() {
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SessionManager.init(applicationContext)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        binding.lifecycleOwner = this

        userViewModel.user.observe(this) { user ->
            binding.user = user
        }

        userViewModel.error.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }

        userViewModel.fetchLoggedUser()

        binding.btnChangePassword.setOnClickListener {
            startActivity(Intent(this, ChangePasswordActivity::class.java))
        }

        binding.imgAvatar.setOnClickListener {
            val intent = Intent(this, SelectAvatarActivity::class.java)
            intent.putExtra("fromProfile", true)
            startActivity(intent)
        }
    }
}

