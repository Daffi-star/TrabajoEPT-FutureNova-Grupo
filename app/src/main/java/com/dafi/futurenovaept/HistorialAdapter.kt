package com.dafi.futurenovaept

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistorialAdapter(private val listaRegistros: List<RegistroAgua>) :
    RecyclerView.Adapter<HistorialAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCantidad: TextView = view.findViewById(R.id.tvCantidad)
        val tvFecha: TextView = view.findViewById(R.id.tvFecha)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val registro = listaRegistros[position]
        holder.tvCantidad.text = "${registro.cantidad} ml"
        holder.tvFecha.text = registro.fecha
    }

    override fun getItemCount(): Int = listaRegistros.size
}