package com.dafi.futurenovaept

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class DiagnosisActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diagnosis)

        val option1 = findViewById<LinearLayout>(R.id.option1)
        val option2 = findViewById<LinearLayout>(R.id.option2)
        val option3 = findViewById<LinearLayout>(R.id.option3)
        val option4 = findViewById<LinearLayout>(R.id.option4)

        val check1 = findViewById<ImageView>(R.id.check1)
        val check2 = findViewById<ImageView>(R.id.check2)
        val check3 = findViewById<ImageView>(R.id.check3)
        val check4 = findViewById<ImageView>(R.id.check4)

        val options = listOf(option1, option2, option3, option4)
        val checks = listOf(check1, check2, check3, check4)

        options.forEachIndexed { index, option ->
            option.setOnClickListener {
                options.forEachIndexed { i, other ->
                    other.isSelected = false
                    checks[i].visibility = View.GONE
                }
                option.isSelected = true
                checks[index].visibility = View.VISIBLE
            }
        }
    }
}
