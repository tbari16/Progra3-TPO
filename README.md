Antes de la explicación del código, algunas aclaraciones sobre como ejecutar el código:
- El archivo principal es el Main que al ejecutarlo permitirá ver un menú en consola para poder ver las 3 funcionalidades.
- Los archivos que empiezan por "Prueba" son los casos de prueba de cada funcionalidad: PruebasOrdenamientoPublicaciones corresponde a la funcionalidad 1, PruebasDijkstra corresponde a la funcionalidad 2 y PruebasSeleccionIniciativas corresponde a la funcionalidad 3. Dentro del código está comentado que hace cada prueba y que busca verificar.


# Funcionalidades Obligatorias

## Funcionalidad 1 - Organización del muro de publicaciones

### 1. Análisis del problema

El problema consiste en organizar las publicaciones de una red social universitaria para que los usuarios puedan consultar primero las más recientes o las más relevantes.

Cada publicación posee un identificador, un autor, una fecha y hora, una cantidad de reacciones y cantidad de comentarios. Para calcular la relevancia, se suman las reacciones y los comentarios de cada publicación.

El ordenamiento por fecha debe mostrar primero las publicaciones más recientes. En cambio, el ordenamiento por relevancia debe mostrar primero las que tienen mayor cantidad de reacciones y comentarios. Si dos publicaciones tienen la misma relevancia, se prioriza la más reciente y, si también coinciden en fecha y hora, se utiliza el identificador menor.

### 2. Selección de la estrategia y justificación

La estrategia seleccionada es Divide y Conquista, mediante el algoritmo MergeSort.

Elegimos esta estrategia porque permite ordenar grandes cantidades de publicaciones de manera eficiente. El algoritmo divide la lista en partes más pequeñas, ordena cada parte por separado y luego las combina respetando el orden establecido.

Además, permite trabajar con un costo temporal de O(n log n), por lo que resulta adecuado cuando aumenta la cantidad de publicaciones.

### 3. Explicación general de la implementación

En la clase Publicación se encuentran los métodos que permiten ordenar las publicaciones por fecha o por relevancia. Ambos crean una copia de la lista original y aplican MergeSort sobre ella.

El algoritmo divide la lista en dos partes hasta llegar a secciones de un solo elemento. Luego, mediante el método merge, combina esas partes comparando las publicaciones y colocándolas en el orden correcto.

El método comparar establece los criterios de ordenamiento. Para la fecha, prioriza la más reciente y luego el identificador menor. Para la relevancia, primero compara la suma de reacciones y comentarios y, en caso de empate, aplica los mismos criterios de fecha e identificador.

Finalmente, el programa muestra las publicaciones ordenadas sin modificar la lista original.

### 4. Otras estrategias seleccionadas

Una alternativa sería utilizar el ordenamiento por selección o por inserción. Estos algoritmos son más sencillos de implementar, pero pueden necesitar un tiempo de O(n²) cuando aumenta la cantidad de publicaciones. Por este motivo, descartamos esta opción ya que en caso de que se desee trabajar con grandes volumenes de datos, no es la mejor opcion.

También podría utilizarse QuickSort, que tiene una complejidad de O(n log n), aunque puede llegar a O(n²) en el peor caso. MergeSort mantiene una complejidad de O(n log n) en los distintos casos y permite combinar las partes de manera ordenada.

### 5. Análisis de complejidad temporal

La complejidad temporal de MergeSort es O(n log n), donde n representa la cantidad de publicaciones.

Esto se debe a que el algoritmo divide la lista en dos partes en cada nivel de recursión, lo que genera aproximadamente log n niveles. En cada nivel, el método merge recorre los elementos de las partes para combinarlas, realizando un trabajo total proporcional a n.

Por lo tanto, el costo total es O(n log n). Esta complejidad permite ordenar las publicaciones de forma eficiente a medida que aumenta su cantidad.

## Funcionalidad 2 - Cadena de difusión más rápida

### 1. Análisis del problema

La funcionalidad consiste en encontrar la cadena de contactos que permita transmitir una convocatoria desde un usuario de origen hasta otro de destino en el menor tiempo posible.

Los datos que vamos a tener para resolver este problema es una red de usuarios, en donde cada uno puede tener vínculos con otros usuarios y cada vínculo tiene un tiempo asociado de transmisión (el cual está en minutos). Una cosa a tener en cuenta es que los usuarios pueden no tener vínculo con otros usuarios.

Los datos de entrada van a ser:

- Usuarios que forman parte de la red social.

- Vínculos habilitados entre los usuarios.

- Tiempo estimado de transmisión de cada vínculo.

- Usuario de origen y usuario de destino.

Las restricciones que encontramos fueron las siguientes:
Los vínculos tienen un sentido de transmisión. Es decir que el vínculo es dirigido (si un usuario 1 tiene vínculo con el usuario 2, no necesariamente el usuario 2 tiene vínculo con el usuario 1); Además, los tiempos de transmisión son positivos; También, entendemos que los tiempos son fijos y se suman a medida que se recorre una cadena; Por último, puede ocurrir que no exista una cadena que conecte el origen con el destino.

El objetivo es lograr una cadena de usuarios que permita transmitir una convocatoria en el menor tiempo total posible, mostrando los usuarios involucrados y la duración estimada. Además de que si no existe una cadena válida, el sistema debe notificar esa imposibilidad.

### 2. Selección de la estrategia y justificación

La estrategia seleccionada para resolver el problema fue **Greedy** usando el algoritmo de **Dijkstra**.

La razón por la que decidimos utilizar esta estrategia y algoritmo es porque el problema se puede representar con un grafo dirigido y ponderado en el que cada vértice o nodo representa a los usuarios, las aristas representan los vínculos y el peso de las aristas los tiempos de transmisión.

Sabiendo que se puede representar como un grafo dirigido, nosotros sabemos que Dijkstra permite encontrar los caminos de menor costo desde un vértice de origen hacia los demás vértices alcanzables del grafo. Además, sabemos que forma parte de la estrategia de Greedy porque en cada iteración, se selecciona al usuario pendiente que tenga menor tiempo acumulado conocido desde el origen y una vez que se seleccionó no se vuelve a reconsiderar.

Esta estrategia es adecuada porque todos los tiempos de transmisión son positivos y esto nos permite garantizar que el algoritmo encuentre un camino de tiempo mínimo. A parte, esta implementación va a permitirnos guardar la información necesaria para reconstruir la cadena de usuarios y mostrar las decisiones tomadas durante el cálculo.

### 3. Explicación general de la implementación

Para poder implementar esta funcionalidad, creamos una clase Grafo que va a representar la red de usuarios mediante una matriz de adyacencia. En esta matriz se almacenan los tiempos de transmisión entre los usuarios que tienen vínculos y cuando no existe una conexión directa, se usa -1. Luego, el algoritmo de Dijkstra se implementó siguiendo el pseudocódigo que vimos en clase.

Primero, se toma el usuario de origen y se lo incorpora al conjunto de visitados. Después se crea el grafo auxiliar para almacenar los mejores tiempos conocidos desde el origen hacia el resto de usuarios (al principio este grafo tiene los tiempos de vínculos directos que salen del origen). Además, se usa un conjunto de usuarios pendientes que son aquellos usuarios que tienen tiempos mínimos no confirmados todavía.

Una vez hecho eso, empieza el proceso principal del algoritmo ya que en cada iteración se selecciona al usuario pendiente con el menor tiempo desde el origen y se lo marca como visitado.

Después, se analizan los vínculos con el resto de usuarios pendientes para ver si, pasando por el usuario seleccionado, se puede encontrar una cadena más rápida. Si el nuevo tiempo es menor que el que estaba registrado, se actualiza. Este proceso se repite hasta procesar todos los usuarios alcanzables y cuando no queden más usuarios, finaliza.

Ahora bien, para poder mostrar la cadena completa, incorporamos un arreglo que guarda el usuario anterior de cada vértice en el mejor recorrido encontrado. Esto lo hicimos para que se pueda reconstruir la cadena desde el destino hasta el origen y mostrarla en el orden correcto.

Finalmente, el menú que creamos para poder realizar una especie de interfaz de consola, permite seleccionar un origen y un destino, consultar la cadena más rápida y visualizar las principales decisiones tomadas durante la ejecución.

### 4. Otras estrategias seleccionadas

La estrategia que también estuvo en consideración para resolver esta funcionalidad fue la siguiente:

- Programación dinámica con el algoritmo de Floyd: Este algoritmo nos permitiría calcular los caminos mínimos entre todos los pares de vértices de un grafo. Sin embargo, para esta funcionalidad solamente queremos calcular la cadena más rápida desde un usuario de origen hasta uno de destino. Usando Floyd estaríamos calculando también los caminos entre muchos otros pares de usuarios que no necesitamos ni queremos consultar.

Otra decisión que nos hizo descartar este algoritmo fue la complejidad temporal ya que Floyd tiene una complejidad de O(n³) mientras que Dijkstra tiene una complejidad de O(n²).

Por otro lado, en un primer análisis grupal se pensaron en estrategias como Backtracking o la utilización de Prim o Kruskal, sin embargo se descartaron rápidamente por las siguientes razones: Backtracking haría que se calculen las diferentes cadenas entre el origen y el destino pero esta estrategia puede llegar a explorar una gran cantidad de caminos a medida que crecen los usuarios y vínculos. Luego, Prim y Kuskal se descartaron porque están orientados a encontrar o calcular un árbol de recubrimiento mínimo y no a calcular el camino mínimo o más corto entre dos usuarios.

### 5. Análisis de complejidad temporal

Como ya mencionamos anteriormente, la implementación de Dijkstra usa una matriz de adyacencia y hace la selección de los usuarios pendientes mediante recorridos sobre los vértices.

Primero se crean las estructuras necesarias y se identifican los vínculos desde el origen. Después, empieza un ciclo principal que puede repetirse hasta n - 1 veces, donde n representa la cantidad de usuarios en el grafo. 

En cada iteración se recorren los usuarios pendientes para seleccionar aquel que tenga el menor tiempo acumulado, esta parte tiene una complejidad de O(n).  
Después se recorren los usuarios pendientes para verificar si existe o no una conexión que mejore los tiempos que están registrados. Esta operación también tiene una complejidad de O(n).

Entonces, como el ciclo principal se ejecuta n veces y en cada iteración se hacen recorrido de n elementos, la complejidad temporal es de O(n²). Volvemos a aclarar que n representa la cantidad de usuarios de la red.

## Funcionalidad 3 - Selección de iniciativas para financiar

### 1. Análisis del problema

Esta funcionalidad consiste en seleccionar un conjunto de iniciativas universitarias para financiar teniendo en cuenta que la universidad dispone de un presupuesto limitado. Cada iniciativa tiene asociado un costo y un puntaje de impacto, y el objetivo es conseguir el mayor impacto total posible sin superar el presupuesto disponible.

Los datos de entrada van a ser:

- Las iniciativas disponibles para financiar.

- El identificador de cada iniciativa.

- El costo de cada iniciativa.

- El puntaje de impacto de cada iniciativa.

- El presupuesto total disponible.

Las restricciones que encontramos fueron:

- El costo de cada iniciativa es un número entero positivo.

- El impacto de cada iniciativa es un número entero no negativo.

- Cada iniciativa puede financiarse una sola vez.

- Una iniciativa debe financiarse en su totalidad, por lo tanto no es posible financiar solamente una parte.

- La suma de los costos de las iniciativas seleccionadas no puede superar el presupuesto disponible.

El objetivo es encontrar la combinación de iniciativas que produzca el mayor impacto total posible respetando el presupuesto. Además, el sistema debe mostrar cuáles fueron las iniciativas seleccionadas, el costo total utilizado, el impacto total obtenido y cuánto presupuesto quedó disponible.

### 2. Selección de la estrategia y justificación

La estrategia seleccionada para resolver el problema fue Programación Dinámica utilizando el problema de la Mochila 0/1.

Elegimos esta estrategia porque existe una relación directa entre el problema planteado y el problema de la mochila. En nuestro caso, cada iniciativa puede considerarse como un objeto de la mochila, el costo de la iniciativa representa su peso, el puntaje de impacto representa su valor y el presupuesto disponible representa la capacidad máxima de la mochila.

Se utiliza específicamente la variante 0/1 porque cada iniciativa tiene solamente dos posibilidades: ser seleccionada o no ser seleccionada. No es posible elegir una iniciativa más de una vez ni financiar una parte de ella.

La Programación Dinámica resulta adecuada porque permite construir la solución óptima a partir de soluciones de problemas más pequeños. Para cada iniciativa y para cada valor posible de presupuesto se guarda el mayor impacto que puede obtenerse hasta ese momento.

De esta forma, al analizar una iniciativa se comparan dos posibilidades: no financiar y conservar el mejor impacto obtenido anteriormente, o financiar y sumar su impacto al mejor resultado que se podía obtener con el presupuesto restante. Entre ambas alternativas se guarda la que produzca el mayor impacto.

Esto permite analizar las distintas combinaciones posibles sin tener que volver a calcular repetidamente los mismos subproblemas.

### 3. Explicación general de la implementación

Para implementar esta funcionalidad creamos una clase Iniciativa, que contiene el identificador, el costo y el impacto de cada iniciativa.

Después, en la clase encargada de realizar la selección, utilizamos una matriz de Programación Dinámica. La matriz tiene una fila por cada iniciativa y una columna por cada valor posible del presupuesto, desde cero hasta el presupuesto máximo disponible.

Cada posición de la matriz representa el mayor impacto que puede conseguirse utilizando una determinada cantidad de iniciativas y teniendo como límite un determinado presupuesto.

La matriz se completa de forma progresiva. Para cada iniciativa se analiza primero si su costo supera el presupuesto que se está evaluando. Si lo supera, esa iniciativa no puede seleccionarse y se conserva el resultado obtenido anteriormente.

En cambio, si la iniciativa puede ser financiada, se comparan dos alternativas:

- No seleccionar la iniciativa actual y mantener el impacto que ya se había obtenido.

- Seleccionar la iniciativa actual, sumar su impacto y agregarlo al mejor resultado obtenido con el presupuesto que quedaría disponible.

El algoritmo guarda en la matriz el mayor valor entre estas dos alternativas.

Una vez completada toda la matriz, la última posición contiene el mayor impacto posible para el presupuesto disponible.

Luego realizamos un recorrido inverso de la matriz para reconstruir la solución. Se compara el valor de cada fila con el de la fila anterior. Si el valor cambia, significa que esa iniciativa fue seleccionada, por lo que se guarda y se descuenta su costo del presupuesto que se está reconstruyendo.

Finalmente, el programa muestra las iniciativas seleccionadas, el costo total utilizado, el impacto total obtenido y el presupuesto restante.

### 4. Otras estrategias seleccionadas

Una de las estrategias que se consideró para resolver este problema fue Greedy.

Una posible solución Greedy sería ordenar las iniciativas utilizando algún criterio, por ejemplo seleccionar primero la de mayor impacto o la que tenga una mejor relación entre impacto y costo. Sin embargo, esta estrategia no garantiza obtener siempre la solución óptima porque las iniciativas no pueden fraccionarse.

Puede ocurrir que una iniciativa tenga individualmente un impacto muy alto, pero que seleccionar otras dos iniciativas de menor impacto individual produzca un impacto total mayor utilizando el mismo presupuesto. Por este motivo, tomar la mejor decisión en cada momento no asegura obtener la mejor combinación final.

También se podría utilizar Backtracking para probar las distintas combinaciones de iniciativas. Esta estrategia permitiría encontrar la solución óptima, ya que para cada iniciativa se podría probar seleccionarla o no seleccionarla.

Sin embargo, la cantidad de combinaciones aumenta considerablemente a medida que crece la cantidad de iniciativas. En el peor caso existirían dos posibilidades para cada iniciativa, por lo que se tendrían que analizar hasta 2^n combinaciones. Por este motivo, consideramos que Programación Dinámica es una alternativa más adecuada para este problema.

Otra posibilidad sería utilizar una solución recursiva basada en dividir el problema entre seleccionar o no cada iniciativa, pero sin almacenar los resultados obtenidos se terminarían resolviendo varias veces los mismos subproblemas. La Programación Dinámica evita este problema almacenando los resultados parciales en la matriz.

### 5. Análisis de complejidad temporal

Para analizar la complejidad temporal consideramos:

- n: cantidad de iniciativas disponibles.

- p: presupuesto máximo disponible.

El algoritmo utiliza un ciclo que recorre las n iniciativas. Dentro de este ciclo existe otro recorrido que analiza todos los valores posibles de presupuesto desde cero hasta p.

En cada posición de la matriz se realizan operaciones de tiempo constante, como comparaciones, sumas y accesos a posiciones de la matriz.

Por lo tanto, la construcción de la matriz tiene una complejidad temporal de:

**O(n * p)**

Después de completar la matriz, se realiza un recorrido inverso sobre las iniciativas para determinar cuáles fueron seleccionadas. Este recorrido tiene una complejidad de O(n).

Entonces, la complejidad total sería:

**O(n * p) + O(n)**

Como el término dominante es O(n * p), la complejidad temporal final del algoritmo es:

**O(n * p)**

donde n representa la cantidad de iniciativas y p representa el presupuesto disponible.

# Funcionalidades Adicionales

Las funcionalidades adicionales elegidas son: A1, B1 y B3
