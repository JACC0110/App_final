package com.example.appfinal.ui.destino

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appfinal.R
import com.example.appfinal.data.model.Destino
import com.example.appfinal.ui.detalle_destino.DestinoActivity
import android.content.Intent

class DestinoAdapter(private val destinos: List<Destino>) :
    RecyclerView.Adapter<DestinoAdapter.DestinoViewHolder>() {

    class DestinoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgDestino: ImageView = view.findViewById(R.id.imgDestino)
        val txtNombre: TextView = view.findViewById(R.id.txtNombre)
        val txtDescripcion: TextView = view.findViewById(R.id.txtDescripcion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_destino, parent, false)
        return DestinoViewHolder(view)
    }

    override fun onBindViewHolder(holder: DestinoViewHolder, position: Int) {
        val destino = destinos[position]
        holder.txtNombre.text = destino.nombre
        holder.txtDescripcion.text = destino.descripcion

        val decodedBytes = Base64.decode(destino.imagen, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        holder.imgDestino.setImageBitmap(bitmap)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DestinoActivity::class.java)
            intent.putExtra("id_destino", destino.id_destino)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = destinos.size
}
