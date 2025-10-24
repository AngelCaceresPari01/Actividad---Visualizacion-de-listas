Nombre: Angel Raul Caceres Pari

En este código, el elemento seleccionado en la lista se actualiza y se recompone de inmediato sin necesidad 
de hacer scroll porque la lista se creó usando un estado observable (mutableStateListOf) dentro de remember. 
Esto significa que cada posición de la lista está “vinculada” a un estado que Jetpack Compose vigila constantemente. 
Cuando el usuario cambia el contenido de un elemento (por ejemplo, al presionar el botón “Actualizar”),
se modifica directamente la posición correspondiente de esa lista observable. Compose detecta automáticamente ese 
cambio en el estado y recompone solo el ítem afectado dentro de la LazyColumn, sin redibujar toda la lista ni esperar 
a que el usuario haga scroll. Esto ocurre gracias al sistema de recomposición inteligente de Compose, que actualiza
únicamente las partes del árbol de UI que dependen de los valores que cambiaron, garantizando así una interfaz fluida y reactiva.
