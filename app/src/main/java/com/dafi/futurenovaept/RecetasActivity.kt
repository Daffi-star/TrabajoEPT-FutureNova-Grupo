package com.dafi.futurenovaept

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecetasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_recetas)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerRecetas)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 1. Obtener el riesgo guardado en la encuesta
        val sharedPref = getSharedPreferences("MisDatosUsuario", MODE_PRIVATE)
        val riesgoUsuario = sharedPref.getString("nivel_riesgo", "bajo") ?: "bajo"

        // 2. Lista completa de recetas (Todas dentro de un único listOf con sus 7 parámetros)
        val listaCompleta = listOf(
            // --- NIVEL ALTO ---
            Receta(
                titulo = "Crema de Hígado de Pollo",
                descripcion = "Aporte masivo de hierro hemínico de alta absorción.",
                nivelRiesgo = "alto",
                ingredientes = "• 250g de hígado de pollo\n• 1 cebolla mediana\n• 2 dientes de ajo\n• 1 papa sancochada\n• 3 cucharadas de leche\n• Sal al gusto",
                preparacion = "1. Limpiar bien los hígados.\n2. Sofreír la cebolla y los ajos en una sartén.\n3. Agregar los hígados hasta que estén bien cocidos.\n4. Licuar todo junto con la papa sancochada y la leche hasta obtener una crema homogénea.",
                comoAyuda = "El hígado de pollo es una fuente altísima de hierro hemínico, el cual eleva la hemoglobina rápidamente.",
                datosExtra = "Evita tomar té o café inmediatamente después para no bloquear la absorción."
            ),
            Receta(
                titulo = "Jugo de Remolacha Energético",
                descripcion = "Estimula la producción de glóbulos rojos y combate la fatiga.",
                nivelRiesgo = "alto",
                ingredientes = "• 1 remolacha cruda pequeña\n• 1 zanahoria\n• Jugo de 3 naranjas\n• 1 vaso de agua",
                preparacion = "1. Pelar y trocear la remolacha y la zanahoria.\n2. Licuarlas junto con el jugo puro de naranja y el agua hasta integrar por completo.",
                comoAyuda = "Aporta fitonutrientes y vitamina C que potencian la asimilación de minerales.",
                datosExtra = "Tómalo preferentemente recién hecho para aprovechar al máximo sus nutrientes."
            ),
            Receta(
                titulo = "Bistec de Res con Brócoli",
                descripcion = "Carne roja rica en hierro combinada con vitaminas esenciales.",
                nivelRiesgo = "alto",
                ingredientes = "• 1 filete de res magro\n• 1 taza de brócoli al vapor\n• 1 diente de ajo\n• Aceite de oliva y sal",
                preparacion = "1. Sazonar el filete de res con ajo y cocinarlo a la plancha con poco aceite.\n2. Servir acompañado del brócoli cocido al vapor.",
                comoAyuda = "La carne roja provee hierro directo de gran calidad para contrarrestar la anemia.",
                datosExtra = "Añade un chorrito de limón al brócoli para potenciar la absorción de nutrientes."
            ),
            Receta(
                titulo = "Paté Casero de Hígado",
                descripcion = "Opción práctica para untar y consumir hierro de forma constante.",
                nivelRiesgo = "alto",
                ingredientes = "• 200g de hígado de pollo\n• 2 cucharadas de mantequilla\n• 1/2 cebolla picada\n• Pizca de sal y pimienta",
                preparacion = "1. Cocinar el hígado con la cebolla hasta que esté listo.\n2. Procesar en la licuadora o picadora junto con la mantequilla hasta lograr una pasta suave.",
                comoAyuda = "Permite incorporar porciones frecuentes de hierro hemínico en desayunos o meriendas.",
                datosExtra = "Úntalo en pan integral para un tentempié nutritivo."
            ),
            Receta(
                titulo = "Hígado Salteado Estilo Peruano",
                descripcion = "Clásico plato rico en hierro con un toque cítrico.",
                nivelRiesgo = "alto",
                ingredientes = "• 200g de hígado de res en tiras\n• 1 cebolla en juliana\n• 1 tomate\n• Vinagre y papas sancochadas",
                preparacion = "1. Saltear el hígado en tiras con la cebolla y el tomate a fuego alto.\n2. Agregar un chorrito de vinagre y servir con papas sancochadas.",
                comoAyuda = "El vinagre y los jugos vegetales ayudan a la digestión y absorción del mineral.",
                datosExtra = "Un plato contundente ideal para el almuerzo principal."
            ),
            Receta(
                titulo = "Albóndigas de Carne en Salsa",
                descripcion = "Combinación de carne magra de res y tomate natural.",
                nivelRiesgo = "alto",
                ingredientes = "• 250g de carne molida de res\n• 1 huevo\n• Tomates triturados\n• Ajo y albahaca",
                preparacion = "1. Mezclar la carne con el huevo y formar pequeñas esferas.\n2. Cocinarlas sumergidas en la salsa de tomate natural hasta que estén bien cocidas.",
                comoAyuda = "Aporta proteínas y hierro esenciales para la formación de glóbulos rojos.",
                datosExtra = "Ideal para acompañar con una porción moderada de arroz integral."
            ),

            // --- NIVEL MODERADO ---
            Receta(
                titulo = "Guiso de Lentejas Nutritivo",
                descripcion = "Fuente tradicional de hierro vegetal y fibra soluble.",
                nivelRiesgo = "moderado",
                ingredientes = "• 1 taza de lentejas\n• 1 zanahoria en cubos\n• 1 cebolla\n• Caldo de verduras y comino",
                preparacion = "1. Hacer un aderezo base con cebolla y comino.\n2. Añadir las lentejas remojadas, la zanahoria y el caldo.\n3. Cocinar a fuego medio por 35 minutos.",
                comoAyuda = "Aporta hierro no hemínico y energía sostenida para combatir el cansancio.",
                datosExtra = "Acompaña con un vaso de limonada para duplicar la absorción del hierro."
            ),
            Receta(
                titulo = "Tortilla de Avena y Acelga",
                descripcion = "Desayuno o cena ligera rica en fibra y minerales.",
                nivelRiesgo = "moderado",
                ingredientes = "• 1 taza de acelga picada\n• 3 cdas de avena\n• 2 huevos\n• Sal y pimienta",
                preparacion = "1. Batir los huevos y mezclar con la avena y la acelga picada fina.\n2. Verter en una sartén antiadherente y dorar por ambos lados.",
                comoAyuda = "La acelga aporta folatos y la avena otorga saciedad y vitalidad.",
                datosExtra = "Excelente alternativa ligera para empezar el día con energía."
            ),
            Receta(
                titulo = "Sopa de Quinua con Pollo",
                descripcion = "Pseudocereal completo rico en proteínas y minerales.",
                nivelRiesgo = "moderado",
                ingredientes = "• 1/2 taza de quinua lavada\n• 1 pechuga de pollo desmenuzada\n• Papa amarilla y verduras",
                preparacion = "1. Hervir el pollo con las verduras para hacer un caldo.\n2. Agregar la quinua lavada y la papa amarilla, cocinar hasta que todo esté tierno.",
                comoAyuda = "La quinua contiene aminoácidos esenciales y minerales que fortalecen el organismo.",
                datosExtra = "Muy reconfortante para los días fríos."
            ),
            Receta(
                titulo = "Pescado al Horno con Limón",
                descripcion = "Proteínas magras y ácidos grasos saludables.",
                nivelRiesgo = "moderado",
                ingredientes = "• 1 filete de pescado\n• 2 limones\n• Ajo y aceite de oliva\n• Camote sancochado",
                preparacion = "1. Condimentar el pescado con ajo, aceite de oliva y jugo de limón.\n2. Hornear por 15 minutos y servir con camote.",
                comoAyuda = "Facilita una nutrición equilibrada protegiendo el sistema circulatorio.",
                datosExtra = "El camote aporta carbohidratos complejos de absorción lenta."
            ),
            Receta(
                titulo = "Batido de Fresa, Remolacha y Naranja",
                descripcion = "Bebida refrescante cargada de antioxidantes.",
                nivelRiesgo = "moderado",
                ingredientes = "• 1 remolacha pequeña cocida\n• 1 taza de fresas\n• Jugo de naranja puro",
                preparacion = "1. Colocar todos los ingredientes en la licuadora.\n2. Procesar hasta conseguir una mezcla homogénea y beber al instante.",
                comoAyuda = "Aporta una gran cantidad de vitamina C y fitonutrientes estimulantes.",
                datosExtra = "Refrescante para media mañana."
            ),
            Receta(
                titulo = "Arroz Chaufa de Quinua",
                descripcion = "Versión saludable cambiando el arroz tradicional por quinua.",
                nivelRiesgo = "moderado",
                ingredientes = "• 1 taza de quinua cocida\n• 1 pechuga en cubos\n• 1 huevo\n• Sillao bajo en sodio y cebollita china",
                preparacion = "1. Saltear el pollo en cubos.\n2. Agregar la quinua cocida, el huevo revuelto y mezclar con el sillao y la cebollita china.",
                comoAyuda = "Nutritivo plato completo que aporta energía y micronutrientes.",
                datosExtra = "Una opción creativa para incorporar granos andinos."
            ),
            Receta(
                titulo = "Ensalada de Remolacha y Zanahoria",
                descripcion = "Acompañamiento fresco lleno de vitaminas.",
                nivelRiesgo = "moderado",
                ingredientes = "• 1 remolacha rallada\n• 1 zanahoria rallada\n• Limón y aceite de oliva",
                preparacion = "1. Rallar la remolacha y la zanahoria crudas.\n2. Mezclar en un bol y aliñar con limón y aceite de oliva.",
                comoAyuda = "Estimula la digestión y aporta vitaminas del complejo B.",
                datosExtra = "Ideal para acompañar cualquier segundo plato."
            ),
            Receta(
                titulo = "Caldo Verde de Pollo y Espinaca",
                descripcion = "Caldo reconstituyente para recuperar energías.",
                nivelRiesgo = "moderado",
                ingredientes = "• Presas de pollo\n• Hojas de espinaca fresca\n• Fideos finos y papa",
                preparacion = "1. Hervir el pollo con la papa y los fideos.\n2. Al final, añadir las hojas de espinaca por un par de minutos y apagar.",
                comoAyuda = "Aporta hidratación, sales minerales y nutrientes de la hoja verde.",
                datosExtra = "Excelente opción para cenas ligeras y reparadoras."
            ),

            // --- NIVEL BAJO ---
            Receta(
                titulo = "Ensalada de Espinacas y Lentejas",
                descripcion = "Rica en hierro vegetal y ácido fólico para mantenimiento.",
                nivelRiesgo = "bajo",
                ingredientes = "• 2 tazas de espinacas baby\n• 1 taza de lentejas cocidas\n• Tomate y limón",
                preparacion = "1. Mezclar las espinacas con las lentejas cocidas y el tomate picado.\n2. Aliñar con jugo de limón y aceite de oliva.",
                comoAyuda = "Ayuda a mantener estables los niveles preventivos de hierro en la sangre.",
                datosExtra = "El limón ayuda a la correcta asimilación de los nutrientes vegetales."
            ),
            Receta(
                titulo = "Jugo Verde de Naranja y Espinaca",
                descripcion = "Desayuno rápido que fusiona vitamina C y hierro.",
                nivelRiesgo = "bajo",
                ingredientes = "• 1 taza de espinacas frescas\n• Jugo de 3 naranjas",
                preparacion = "1. Licuar las hojas limpias de espinaca junto con el jugo de naranja recién exprimido.",
                comoAyuda = "Excelente aporte matutino de antioxidantes y micronutrientes.",
                datosExtra = "Consumir inmediatamente para evitar la pérdida de vitamina C."
            ),
            Receta(
                titulo = "Ensalada de Garbanzos y Tomate",
                descripcion = "Fuente de fibra, proteínas vegetales y minerales.",
                nivelRiesgo = "bajo",
                ingredientes = "• 1 taza de garbanzos cocidos\n• Tomates cherry\n• Pepino y perejil",
                preparacion = "1. Combinar los garbanzos cocidos con los tomates cherry partidos y el pepino en cubos.\n2. Agregar perejil picado y aliñar.",
                comoAyuda = "Apoya el tránsito intestinal y la nutrición general equilibrada.",
                datosExtra = "Una receta muy saciante y fácil de preparar."
            ),
            Receta(
                titulo = "Tostadas Integrales con Aguacate y Huevo",
                descripcion = "Aporta grasas saludables y proteínas de gran calidad.",
                nivelRiesgo = "bajo",
                ingredientes = "• Pan integral\n• 1 aguacate\n• 1 huevo poché o revuelto\n• Semillas de sésamo",
                preparacion = "1. Tostar el pan integral.\n2. Machacar el aguacate encima y colocar el huevo preparado arriba con un toque de sésamo.",
                comoAyuda = "Mantiene los niveles de energía estables durante la mañana.",
                datosExtra = "Un clásico desayuno moderno muy nutritivo."
            ),
            Receta(
                titulo = "Sopa Crema de Espinacas",
                descripcion = "Suave y reconfortante, perfecta para la noche.",
                nivelRiesgo = "bajo",
                ingredientes = "• Espinacas frescas\n• 1 papa pequeña\n• Caldo de verduras y leche",
                preparacion = "1. Cocinar la espinaca y la papa en el caldo.\n2. Licuar con un chorrito de leche hasta obtener una textura tersa.",
                comoAyuda = "Aporta minerales de forma ligera antes de dormir.",
                datosExtra = "Ideal para una cena digestiva y reconfortante."
            ),
            Receta(
                titulo = "Hamburguesas de Lentejas y Avena",
                descripcion = "Opción vegetariana económica, rica en fibra y minerales.",
                nivelRiesgo = "bajo",
                ingredientes = "• Lentejas cocidas\n• Harina de avena\n• Cebolla fina, comino y pimentón",
                preparacion = "1. Aplastar las lentejas y mezclarlas con la avena y los condimentos.\n2. Formar medallones y dorarlos en una sartén con poco aceite.",
                comoAyuda = "Aporta una excelente alternativa libre de carnes para sumar nutrientes.",
                datosExtra = "Puedes servirlas al plato con una ensalada fresca."
            )
        )

        // 3. FILTRAR Y ORDENAR: Las que coincidan con el riesgo del usuario van primero
        val listaOrdenada = listaCompleta.sortedByDescending { it.nivelRiesgo.equals(riesgoUsuario, ignoreCase = true) }

        // 4. Mostrar en el RecyclerView
        recyclerView.adapter = RecetasAdapter(listaOrdenada)
    }
}