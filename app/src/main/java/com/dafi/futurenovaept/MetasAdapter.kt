package com.dafi.futurenovaept

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MetasAdapter(
    private val lista: MutableList<MetaItem>,
    private val onCheckChanged: (MetaItem, Boolean) -> Unit,
    private val onEliminarClick: (MetaItem) -> Unit
) : RecyclerView.Adapter<MetasAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cbCompletada: CheckBox = view.findViewById(R.id.cbMetaCompletada)
        val tvTitulo: TextView = view.findViewById(R.id.tvTituloMeta)
        val tvDesc: TextView = view.findViewById(R.id.tvDescMeta)
        val btnEliminar: ImageView = view.findViewById(R.id.btnEliminarMeta)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meta, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]
        holder.tvTitulo.text = item.titulo
        holder.tvDesc.text = item.descripcion

        holder.cbCompletada.setOnCheckedChangeListener(null)
        holder.cbCompletada.isChecked = item.completada
        aplicarEstiloTachado(holder.tvTitulo, holder.tvDesc, item.completada)

        holder.cbCompletada.setOnCheckedChangeListener { _, isChecked ->
            item.completada = isChecked
            aplicarEstiloTachado(holder.tvTitulo, holder.tvDesc, isChecked)
            onCheckChanged(item, isChecked)
        }

        holder.btnEliminar.setOnClickListener {
            onEliminarClick(item)
        }
    }

    private fun aplicarEstiloTachado(tvTitulo: TextView, tvDesc: TextView, completada: Boolean) {
        if (completada) {
            tvTitulo.paintFlags = tvTitulo.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            tvTitulo.setTextColor(android.graphics.Color.parseColor("#999999"))
            tvDesc.setTextColor(android.graphics.Color.parseColor("#BBBBBB"))
        } else {
            tvTitulo.paintFlags = tvTitulo.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            tvTitulo.setTextColor(android.graphics.Color.parseColor("#2E5A44"))
            tvDesc.setTextColor(android.graphics.Color.parseColor("#666666"))
        }
    }

    override fun getItemCount() = lista.size
}