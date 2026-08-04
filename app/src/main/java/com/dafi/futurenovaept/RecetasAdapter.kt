package com.dafi.futurenovaept

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecetasAdapter(private val listaRecetas: List<Receta>) :
    RecyclerView.Adapter<RecetasAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitulo: TextView = view.findViewById(R.id.tvTituloReceta)
        val tvRiesgo: TextView = view.findViewById(R.id.tvRiesgoReceta)
        val tvDesc: TextView = view.findViewById(R.id.tvDescReceta)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_receta, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val receta = listaRecetas[position]
        holder.tvTitulo.text = receta.titulo
        holder.tvDesc.text = receta.descripcion
        holder.tvRiesgo.text = "Ideal para nivel: ${receta.nivelRiesgo.uppercase()}"

        // Hacer la tarjeta clickeable para abrir el detalle completo
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetalleRecetaActivity::class.java).apply {
                putExtra("EXTRA_TITULO", receta.titulo)
                putExtra("EXTRA_RIESGO", receta.nivelRiesgo)
                putExtra("EXTRA_INGREDIENTES", receta.ingredientes)
                putExtra("EXTRA_PREPARACION", receta.preparacion)
                putExtra("EXTRA_COMO_AYUDA", receta.comoAyuda)
                putExtra("EXTRA_DATOS_EXTRA", receta.datosExtra)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = listaRecetas.size
}