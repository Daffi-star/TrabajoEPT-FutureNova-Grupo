package com.dafi.futurenovaept

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SuplementosAdapter(
    private val lista: MutableList<SuplementoAlarma>,
    private val onEliminarClick: (SuplementoAlarma) -> Unit
) : RecyclerView.Adapter<SuplementosAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tvNombreSuplemento)
        val tvHora: TextView = view.findViewById(R.id.tvHoraSuplemento)
        val btnEliminar: ImageView = view.findViewById(R.id.btnEliminarAlarma)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_suplemento, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]
        holder.tvNombre.text = item.nombre
        holder.tvHora.text = item.hora

        holder.btnEliminar.setOnClickListener {
            onEliminarClick(item)
        }
    }

    override fun getItemCount() = lista.size
}