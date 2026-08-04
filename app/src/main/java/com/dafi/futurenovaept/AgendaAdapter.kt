package com.dafi.futurenovaept

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AgendaAdapter(
    private val lista: MutableList<AgendaItem>,
    private val onCheckChanged: (AgendaItem, Boolean) -> Unit,
    private val onEliminarClick: (AgendaItem) -> Unit
) : RecyclerView.Adapter<AgendaAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cbCompletada: CheckBox = view.findViewById(R.id.cbCompletada)
        val tvTitulo: TextView = view.findViewById(R.id.tvTituloAgenda)
        val tvCategoria: TextView = view.findViewById(R.id.tvCategoria)
        val tvFechaHora: TextView = view.findViewById(R.id.tvFechaHora)
        val btnEliminar: ImageView = view.findViewById(R.id.btnEliminarAgenda)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_agenda, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]
        holder.tvTitulo.text = item.titulo
        holder.tvCategoria.text = item.categoria
        holder.tvFechaHora.text = item.fechaHora

        // Evitar bug visual al reciclar vistas
        holder.cbCompletada.setOnCheckedChangeListener(null)
        holder.cbCompletada.isChecked = item.completada
        aplicarEstiloTachado(holder.tvTitulo, item.completada)

        holder.cbCompletada.setOnCheckedChangeListener { _, isChecked ->
            item.completada = isChecked
            aplicarEstiloTachado(holder.tvTitulo, isChecked)
            onCheckChanged(item, isChecked)
        }

        holder.btnEliminar.setOnClickListener {
            onEliminarClick(item)
        }
    }

    private fun aplicarEstiloTachado(textView: TextView, completada: Boolean) {
        if (completada) {
            textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            textView.setTextColor(android.graphics.Color.parseColor("#999999"))
        } else {
            textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            textView.setTextColor(android.graphics.Color.parseColor("#2E5A44"))
        }
    }

    override fun getItemCount() = lista.size
}