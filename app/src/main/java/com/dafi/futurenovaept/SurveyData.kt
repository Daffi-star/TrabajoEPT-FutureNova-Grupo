package com.dafi.futurenovaept

data class SurveyOption(
    val emoji: String,
    val text: String,
    val isExclusive: Boolean = false
)

data class SurveyQuestion(
    val question: String,
    val subtitle: String? = null,
    val options: List<SurveyOption>,
    val allowMultiple: Boolean = false
)

object SurveyData {
    const val TOTAL_QUESTIONS = 5

    val questions: List<SurveyQuestion> = listOf(
        SurveyQuestion(
            question = "¡Hola! Cuéntanos de ti, ¿cómo te has sentido últimamente?",
            options = listOf(
                SurveyOption("😴", "Me siento cansado(a) con frecuencia"),
                SurveyOption("😐", "A veces me siento cansado(a)"),
                SurveyOption("💪", "Me siento con energía casi siempre"),
                SurveyOption("😍", "Me siento muy bien, lleno(a) de energía")
            )
        ),
        SurveyQuestion(
            question = "¿Con qué frecuencia consumes alimentos ricos en hierro?",
            subtitle = "Ejemplos: sangrecita, hígado, lentejas, espinaca, quinua, pescado.",
            options = listOf(
                SurveyOption("🥩", "Casi nunca"),
                SurveyOption("🥗", "Algunas veces por semana"),
                SurveyOption("🍲", "Casi todos los días")
            )
        ),
        SurveyQuestion(
            question = "¿Has sentido alguno de estos síntomas recientemente?",
            subtitle = "Puedes seleccionar más de una opción.",
            allowMultiple = true,
            options = listOf(
                SurveyOption("😵‍💫", "Mareos o dolor de cabeza"),
                SurveyOption("🧠", "Falta de concentración"),
                SurveyOption("😶", "Palidez"),
                SurveyOption("💤", "Debilidad o falta de energía"),
                SurveyOption("🙂", "Ninguno de estos síntomas", isExclusive = true)
            )
        ),
        SurveyQuestion(
            question = "¿Con qué frecuencia comes frutas y verduras?",
            subtitle = "Las frutas cítricas ayudan a absorber mejor el hierro.",
            options = listOf(
                SurveyOption("🍎", "Casi nunca"),
                SurveyOption("🥕", "Algunas veces por semana"),
                SurveyOption("🥦", "Casi todos los días")
            )
        ),
        SurveyQuestion(
            question = "¿Has realizado análisis de sangre (hemoglobina o hierro) en el último año?",
            options = listOf(
                SurveyOption("🩸", "Sí, y me dijeron que estaba bajo"),
                SurveyOption("✅", "Sí, y estaba normal"),
                SurveyOption("❓", "No, nunca me he hecho uno"),
                SurveyOption("🤷", "No recuerdo / no estoy seguro(a)")
            )
        )
    )
}
