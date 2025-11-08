package com.example.appfinal.ui.avatar

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.appfinal.R
import com.example.appfinal.data.repository.UserRepository
import com.example.appfinal.ui.main.MainActivity
import com.example.appfinal.ui.password.ChangePasswordActivity
import com.example.appfinal.utils.SessionManager
import java.io.ByteArrayOutputStream

class SelectAvatarActivity : AppCompatActivity() {
    private lateinit var imgPreview: ImageView
    private lateinit var btnCamera: Button
    private lateinit var btnGallery: Button
    private lateinit var btnSave: Button
    private lateinit var userRepository: UserRepository

    private var selectedBitmap: Bitmap? = null
    private var fromProfile = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SessionManager.init(applicationContext)
        setContentView(R.layout.activity_select_avatar)

        imgPreview = findViewById(R.id.imgPreview)
        btnCamera = findViewById(R.id.btnCamera)
        btnGallery = findViewById(R.id.btnGallery)
        btnSave = findViewById(R.id.btnSaveAvatar)

        userRepository = UserRepository(this)
        fromProfile = intent.getBooleanExtra("fromProfile", false)

        btnCamera.setOnClickListener { abrirCamara() }
        btnGallery.setOnClickListener { abrirGaleria() }
        btnSave.setOnClickListener {
            selectedBitmap?.let { bitmap ->
                subirAvatar(bitmap)
            } ?: Toast.makeText(this, "Primero selecciona una imagen", Toast.LENGTH_SHORT).show()
        }

        val btnChangePassword = findViewById<Button>(R.id.btnGoChangePassword)
        btnChangePassword.setOnClickListener {
            startActivity(Intent(this, ChangePasswordActivity::class.java))
        }
    }

    private fun abrirCamara() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 200)
        } else {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraLauncher.launch(intent)
        }
    }

    private fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(intent)
    }

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val bitmap = result.data?.extras?.get("data") as? Bitmap
            bitmap?.let {
                imgPreview.setImageBitmap(it)
                selectedBitmap = it
            }
        }
    }

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val uri: Uri? = result.data?.data
            uri?.let {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, it)
                imgPreview.setImageBitmap(bitmap)
                selectedBitmap = bitmap
            }
        }
    }

    private fun subirAvatar(bitmap: Bitmap) {
        val userId = SessionManager.getUserId()
        if (userId == -1) {
            Toast.makeText(this, "No hay usuario autenticado", Toast.LENGTH_SHORT).show()
            return
        }

        val base64 = bitmapToBase64(bitmap)

        userRepository.updateAvatar(userId, base64) { success, message, _ ->
            runOnUiThread {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                if (success) {
                    if (fromProfile) {
                        finish()
                    } else {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val byteArray = baos.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
}

