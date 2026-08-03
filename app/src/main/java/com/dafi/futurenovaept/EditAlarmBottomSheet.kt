package com.dafi.futurenovaept

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.NumberPicker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

// 1. Definimos la interfaz aquí mismo para que el archivo la reconozca
interface OnAlarmUpdatedListener {
    fun onAlarmUpdated(alarmaActualizada: Alarma)
}

class EditAlarmBottomSheet(
    private var alarma: Alarma,
    private val listener: OnAlarmUpdatedListener
) : BottomSheetDialogFragment(R.layout.dialog_edit_alarm) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val npHour = view.findViewById<NumberPicker>(R.id.npHour)
        val npMinute = view.findViewById<NumberPicker>(R.id.npMinute)
        val btnModificar = view.findViewById<Button>(R.id.btnModificar)

        // Configuración de los Pickers
        npHour.minValue = 0
        npHour.maxValue = 23
        npHour.setFormatter { String.format("%02d", it) }

        npMinute.minValue = 0
        npMinute.maxValue = 59
        npMinute.setFormatter { String.format("%02d", it) }

        // Cargar datos actuales de la alarma
        val partes = alarma.hora.split(":")
        npHour.value = partes[0].toInt()
        npMinute.value = partes[1].toInt()

        // Lógica de los CheckBoxes
        val checkBoxes: List<CheckBox> = listOf(
            view.findViewById(R.id.cbL),
            view.findViewById(R.id.cbM),
            view.findViewById(R.id.cbX),
            view.findViewById(R.id.cbJ),
            view.findViewById(R.id.cbV),
            view.findViewById(R.id.cbS),
            view.findViewById(R.id.cbD)
        )

        // Inicializar los CheckBoxes según lo que tenga la alarma
        for (i in 0 until 7) {
            checkBoxes[i].isChecked = alarma.diasRepeticion[i]
        }

        btnModificar.setOnClickListener {
            // Guardar hora
            alarma.hora = String.format("%02d:%02d", npHour.value, npMinute.value)

            // Guardar días
            for (i in 0 until 7) {
                alarma.diasRepeticion[i] = checkBoxes[i].isChecked
            }

            // Llamar al listener (aquí ya no debería marcar error)
            listener.onAlarmUpdated(alarma)
            dismiss()
        }
    }
}