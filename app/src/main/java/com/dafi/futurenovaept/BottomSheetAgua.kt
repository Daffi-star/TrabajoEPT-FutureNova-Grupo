package com.dafi.futurenovaept

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton

class BottomSheetAgua : BottomSheetDialogFragment() {

    // 1. Definimos la interface aquí dentro
    interface OnAguaSelectedListener {
        fun onAguaSelected(cantidad: Int)
    }

    // 2. Creamos la variable que usará la Activity
    var listener: OnAguaSelectedListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_seleccionar_agua, container, false)

        val seekBar = view.findViewById<SeekBar>(R.id.seekBar)
        val tvValor = view.findViewById<TextView>(R.id.tvValor)
        val btnOk = view.findViewById<MaterialButton>(R.id.btnOk)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(s: SeekBar?, p: Int, fromUser: Boolean) {
                tvValor.text = "$p"
            }
            override fun onStartTrackingTouch(s: SeekBar?) {}
            override fun onStopTrackingTouch(s: SeekBar?) {}
        })

        btnOk.setOnClickListener {
            // Obtenemos el valor actual
            val cantidad = tvValor.text.toString().toInt()

            // 3. Enviamos el dato a través del listener
            listener?.onAguaSelected(cantidad)

            dismiss()
        }

        return view
    }
}