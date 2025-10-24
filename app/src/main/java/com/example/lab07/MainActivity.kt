package com.example.lab07

// Importaciones necesarias para Compose y Material Design 3
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Clase principal que representa la actividad (pantalla principal)
class MainActivity : ComponentActivity() {

    // Método que se ejecuta al crear la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContent reemplaza el layout XML tradicional
        // Aquí definimos todo el contenido de la interfaz usando Jetpack Compose
        setContent {
            // Aplica el tema de Material 3
            MaterialTheme {
                // Llama a la función composable principal
                AppScreen()
            }
        }
    }
}

@Composable
fun AppScreen() {
    /*
     * Lista de elementos con estado observable.
     * - remember: mantiene el valor mientras la composición esté activa.
     * - mutableStateListOf: una lista que Compose observa para detectar cambios.
     *   Cada vez que se modifica un elemento, solo ese item se vuelve a componer.
     */
    val items = remember {
        mutableStateListOf<String>().apply {
            addAll((1..100).map { "Elemento $it" }) // Agrega 100 elementos de texto
        }
    }

    // Variables de estado para el formulario
    // `by remember { mutableStateOf(...) }` crea un valor reactivo que
    // hace que Compose actualice la UI cuando cambie su valor.
    var selectedIndex by remember { mutableStateOf("") }  // Número de elemento
    var newContent by remember { mutableStateOf("") }      // Nuevo contenido

    // Layout vertical que contiene el formulario y la lista
    Column(
        modifier = Modifier
            .fillMaxSize()      // Ocupa toda la pantalla
            .padding(16.dp)     // Margen interno
    ) {

        // ---------- FORMULARIO SUPERIOR ----------
        Text(
            "Actualizar elemento",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(8.dp)) // Espaciado entre componentes

        // Campo de texto para ingresar el número del elemento
        OutlinedTextField(
            value = selectedIndex,
            onValueChange = { selectedIndex = it },
            label = { Text("Número de elemento (1-100)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        // Campo de texto para ingresar el nuevo contenido
        OutlinedTextField(
            value = newContent,
            onValueChange = { newContent = it },
            label = { Text("Nuevo contenido") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        // Botón para actualizar el elemento
        Button(
            onClick = {
                // Convertimos el índice a número
                val index = selectedIndex.toIntOrNull()

                // Verificamos que sea un número válido dentro del rango de la lista
                if (index != null && index in 1..items.size) {
                    // Actualizamos el contenido del elemento indicado
                    // Al modificar la lista, Compose detecta el cambio y actualiza solo ese item
                    items[index - 1] = newContent.ifBlank {
                        "Elemento $index (actualizado)"
                    }
                }
            },
            modifier = Modifier.align(Alignment.End) // Alinea el botón a la derecha
        ) {
            Text("Actualizar")
        }

        Spacer(Modifier.height(16.dp))
        Divider(thickness = 1.dp)
        Spacer(Modifier.height(12.dp))

        // ---------- LISTA INFERIOR CON SCROLL ----------
        /*
         * LazyColumn renderiza solo los elementos visibles en pantalla.
         * Esto evita que se creen los 100 composables a la vez, mejorando el rendimiento
         * y evitando calentamiento o lag.
         */
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            // itemsIndexed permite obtener tanto el índice como el valor de cada elemento
            itemsIndexed(items) { index, item ->
                // Muestra el número y el contenido del elemento
                Text(
                    text = "${index + 1}. $item",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
                // Línea divisoria entre ítems
                Divider()
            }
        }
    }
}
