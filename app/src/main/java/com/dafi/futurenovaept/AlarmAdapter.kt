package com.dafi.futurenovaept

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SwitchCompat

class AlarmAdapter(
    var alarmas: List<Alarma>,
    private val onItemClick: (Alarma) -> Unit,       // Para cuando toquen la tarjeta (editar)
    private val onSwitchToggled: (Alarma) -> Unit    // Para cuando muevan el switch
) : RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {

    // 1. Definimos las vistas de cada elemento de la lista
    class AlarmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.tvTituloAlarma)
        val horaTextView: TextView = itemView.findViewById(R.id.tvHoraAlarma)
        val switchAlarma: SwitchCompat = itemView.findViewById(R.id.switchAlarma) // <--- CAMBIADO A SWITCHCOMPAT
    }

    // 2. Crea la vista inflando el XML (este ya lo tenías)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_alarm, parent, false)
        return AlarmViewHolder(view)
    }

    // 3. Conecta los datos con cada elemento visual (¡Aquí está el onBindViewHolder!)
    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val alarma = alarmas[position]

        // Mostramos el nombre y la hora
        holder.nombreTextView.text = alarma.nombre
        holder.horaTextView.text = alarma.hora

        // Limpiamos el listener del switch temporalmente para evitar que se mueva solo al hacer scroll
        holder.switchAlarma.setOnCheckedChangeListener(null)

        // Ponemos el switch en encendido o apagado según la base de datos
        holder.switchAlarma.isChecked = alarma.activo

        // Escuchamos cuando el usuario mueve el switch manualmente
        holder.switchAlarma.setOnCheckedChangeListener { _, isChecked ->
            alarma.activo = isChecked
            onSwitchToggled(alarma) // Avisamos a la Activity para que guarde en la BD y configure la alarma
        }

        // Si hacen clic en toda la tarjeta, abrimos para editar
        holder.itemView.setOnClickListener {
            onItemClick(alarma)
        }
    }

    // 4. Cantidad total de elementos
    override fun getItemCount(): Int = alarmas.size
}