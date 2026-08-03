package com.dafi.futurenovaept

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AlarmAdapter(
    private val clickListener: (Alarma) -> Unit
) : RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {

    var alarmas: MutableList<Alarma> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_alarm, parent, false)
        return AlarmViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val alarma = alarmas[position]
        holder.bind(alarma, clickListener)
    }

    override fun getItemCount(): Int = alarmas.size

    class AlarmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Asegúrate que estos IDs existan en tu item_alarm.xml
        private val txtNombre = itemView.findViewById<TextView>(R.id.tvTituloAlarma)
        private val txtHora = itemView.findViewById<TextView>(R.id.tvHoraAlarma)

        fun bind(alarma: Alarma, clickListener: (Alarma) -> Unit) {
            txtNombre.text = alarma.nombre
            txtHora.text = alarma.hora
            itemView.setOnClickListener { clickListener(alarma) }
        }
    }
}