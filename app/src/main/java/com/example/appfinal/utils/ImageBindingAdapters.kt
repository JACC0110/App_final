package com.example.appfinal.utils

import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.appfinal.R

@BindingAdapter("base64Avatar")
fun loadBase64Avatar(imageView: ImageView, base64String: String?) {
    if (!base64String.isNullOrEmpty()) {
        try {
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
            imageView.setImageBitmap(bitmap)
        } catch (e: Exception) {
            imageView.setImageResource(R.drawable.ic_launcher_background)
        }
    } else {
        imageView.setImageResource(R.drawable.ic_launcher_background)
    }
}
