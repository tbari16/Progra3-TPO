Integrantes: Anton Ignacio; Barisone Tomás; Vargas Facundo.

Antes de la explicación del código, algunas aclaraciones sobre como ejecutar el código:
- El archivo principal es el app.Main que al ejecutarlo permitirá ver un menú en consola para poder ver las 3 funcionalidades.
- Los archivos que empiezan por "Prueba" son los casos de prueba de cada funcionalidad: pruebas.PruebasOrdenamientoPublicaciones corresponde a la funcionalidad 1, pruebas.PruebasDijkstra corresponde a la funcionalidad 2 y pruebas.PruebasSeleccionIniciativas corresponde a la funcionalidad 3. Dentro del código está comentado que hace cada prueba y que busca verificar.


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

La estrategia seleccionada para resolver el problema fue **Greedy** usando el algoritmo de **algoritmos.AlgoritmoDijkstra**.

La razón por la que decidimos utilizar esta estrategia y algoritmo es porque el problema se puede representar con un grafo dirigido y ponderado en el que cada vértice o nodo representa a los usuarios, las aristas representan los vínculos y el peso de las aristas los tiempos de transmisión.

Sabiendo que se puede representar como un grafo dirigido, nosotros sabemos que algoritmos.AlgoritmoDijkstra permite encontrar los caminos de menor costo desde un vértice de origen hacia los demás vértices alcanzables del grafo. Además, sabemos que forma parte de la estrategia de Greedy porque en cada iteración, se selecciona al usuario pendiente que tenga menor tiempo acumulado conocido desde el origen y una vez que se seleccionó no se vuelve a reconsiderar.

Esta estrategia es adecuada porque todos los tiempos de transmisión son positivos y esto nos permite garantizar que el algoritmo encuentre un camino de tiempo mínimo. A parte, esta implementación va a permitirnos guardar la información necesaria para reconstruir la cadena de usuarios y mostrar las decisiones tomadas durante el cálculo.

### 3. Explicación general de la implementación

Para poder implementar esta funcionalidad, creamos una clase algoritmos.Grafo que va a representar la red de usuarios mediante una matriz de adyacencia. En esta matriz se almacenan los tiempos de transmisión entre los usuarios que tienen vínculos y cuando no existe una conexión directa, se usa -1. Luego, el algoritmo de algoritmos.AlgoritmoDijkstra se implementó siguiendo el pseudocódigo que vimos en clase.

Primero, se toma el usuario de origen y se lo incorpora al conjunto de visitados. Después se crea el grafo auxiliar para almacenar los mejores tiempos conocidos desde el origen hacia el resto de usuarios (al principio este grafo tiene los tiempos de vínculos directos que salen del origen). Además, se usa un conjunto de usuarios pendientes que son aquellos usuarios que tienen tiempos mínimos no confirmados todavía.

Una vez hecho eso, empieza el proceso principal del algoritmo ya que en cada iteración se selecciona al usuario pendiente con el menor tiempo desde el origen y se lo marca como visitado.

Después, se analizan los vínculos con el resto de usuarios pendientes para ver si, pasando por el usuario seleccionado, se puede encontrar una cadena más rápida. Si el nuevo tiempo es menor que el que estaba registrado, se actualiza. Este proceso se repite hasta procesar todos los usuarios alcanzables y cuando no queden más usuarios, finaliza.

Ahora bien, para poder mostrar la cadena completa, incorporamos un arreglo que guarda el usuario anterior de cada vértice en el mejor recorrido encontrado. Esto lo hicimos para que se pueda reconstruir la cadena desde el destino hasta el origen y mostrarla en el orden correcto.

Finalmente, el menú que creamos para poder realizar una especie de interfaz de consola, permite seleccionar un origen y un destino, consultar la cadena más rápida y visualizar las principales decisiones tomadas durante la ejecución.

### 4. Otras estrategias seleccionadas

La estrategia que también estuvo en consideración para resolver esta funcionalidad fue la siguiente:

- Programación dinámica con el algoritmo de Floyd: Este algoritmo nos permitiría calcular los caminos mínimos entre todos los pares de vértices de un grafo. Sin embargo, para esta funcionalidad solamente queremos calcular la cadena más rápida desde un usuario de origen hasta uno de destino. Usando Floyd estaríamos calculando también los caminos entre muchos otros pares de usuarios que no necesitamos ni queremos consultar.

Otra decisión que nos hizo descartar este algoritmo fue la complejidad temporal ya que Floyd tiene una complejidad de O(n³) mientras que algoritmos.AlgoritmoDijkstra tiene una complejidad de O(n²).

Por otro lado, en un primer análisis grupal se pensaron en estrategias como Backtracking o la utilización de Prim o Kruskal, sin embargo se descartaron rápidamente por las siguientes razones: Backtracking haría que se calculen las diferentes cadenas entre el origen y el destino pero esta estrategia puede llegar a explorar una gran cantidad de caminos a medida que crecen los usuarios y vínculos. Luego, Prim y Kuskal se descartaron porque están orientados a encontrar o calcular un árbol de recubrimiento mínimo y no a calcular el camino mínimo o más corto entre dos usuarios.

### 5. Análisis de complejidad temporal

Como ya mencionamos anteriormente, la implementación de algoritmos.AlgoritmoDijkstra usa una matriz de adyacencia y hace la selección de los usuarios pendientes mediante recorridos sobre los vértices.

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

Para implementar esta funcionalidad creamos una clase modelo.Iniciativa, que contiene el identificador, el costo y el impacto de cada iniciativa.

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


## Funcionalidad A1 - Consultas de difusión entre todos los usuarios.

### 1. Análisis del problema

El problema consiste en poder consultar el menor tiempo de difusión entre cualquier par de usuarios de la red social, sin tener que repetir todo el cálculo cada vez que se realiza una consulta.

Los datos se representan mediante un grafo dirigido. Cada vértice representa un usuario y cada arista representa un vínculo habilitado para transmitir información en un sentido determinado. Cada vínculo tiene un tiempo positivo, expresado en minutos.

A diferencia de la funcionalidad anterior, en este caso no se busca solamente la cadena más rápida desde un origen determinado, sino que se necesita conocer los caminos mínimos entre todos los pares de usuarios.

El objetivo es realizar un procesamiento inicial de la red, guardar los resultados obtenidos y permitir consultar posteriormente tanto el tiempo mínimo como la cadena correspondiente. También se debe informar cuando no exista una cadena de difusión entre dos usuarios.

### 2. Selección de la estrategia y justificación

La estrategia seleccionada es Programación Dinámica, mediante el algoritmo de Floyd.

Elegimos esta estrategia porque permite calcular los caminos mínimos entre todos los pares de usuarios de un grafo. Para hacerlo, el algoritmo analiza progresivamente si pasar por un usuario intermedio permite encontrar una cadena de menor tiempo entre un origen y un destino.

Esta estrategia resulta adecuada porque la funcionalidad necesita responder numerosas consultas entre distintos usuarios. De esta manera, se realiza el cálculo una sola vez y se guardan los resultados para utilizarlos posteriormente, evitando repetir el procesamiento para cada consulta.

### 3. Explicación general de la implementación

La clase Grafo representa la red de usuarios mediante una matriz de tiempos, donde se almacenan los vínculos directos y sus respectivos minutos. Al tratarse de un grafo dirigido, las conexiones tienen un sentido determinado. Cuando no existe un vínculo directo, se utiliza el valor -1.

La clase AlgoritmoFloyd  contiene el método que realiza el cálculo. Primero, crea dos matrices: una para almacenar las distancias mínimas y otra para registrar el siguiente usuario que se debe visitar para reconstruir cada cadena. Inicialmente, los tiempos entre un usuario y sí mismo son cero, los vínculos directos toman sus tiempos correspondientes y los caminos inexistentes se representan con un valor máximo.

Luego, el algoritmo recorre los usuarios como posibles intermediarios y compara si pasar por uno de ellos permite reducir el tiempo que ya se tenía registrado entre dos usuarios. Si encuentra un camino más rápido, actualiza el tiempo mínimo y guarda el siguiente usuario que permite reconstruir esa cadena.

Finalmente, se utiliza la clase ResultadoFloyd, que conserva las matrices calculadas y permite consultar el tiempo mínimo y reconstruir la cadena correspondiente sin volver a ejecutar Floyd. También permite detectar cuando no existe un camino entre dos usuarios.

### 4. Otras estrategias consideradas

Una alternativa sería utilizar Dijkstra, que permite calcular el camino mínimo desde un usuario de origen hacia los demás usuarios de la red. Sin embargo, para consultar los caminos mínimos entre todos los pares, sería necesario ejecutar el algoritmo repetidamente, tomando cada usuario como origen.

En cambio, Floyd permite realizar todos esos cálculos dentro de un único procesamiento inicial, por lo que resulta más práctico cuando se necesitan realizar numerosas consultas entre distintos pares de usuarios.

Otra posibilidad sería utilizar Backtracking para explorar diferentes cadenas entre cada par de usuarios y quedarse con la de menor tiempo. Sin embargo, esta estrategia podría recorrer una gran cantidad de caminos posibles y generar un costo elevado a medida que aumenta la cantidad de usuarios y conexiones.

Por estos motivos, se eligió Floyd, ya que permite obtener los caminos mínimos entre todos los usuarios y conservar los resultados para realizar consultas posteriores.

### 5. Análisis de complejidad temporal

La complejidad temporal del algoritmo de Floyd es O(n³), donde n representa la cantidad de usuarios de la red.

Esto se debe a que el algoritmo utiliza tres ciclos anidados para recorrer los usuarios: uno para seleccionar el posible intermediario, otro para recorrer los orígenes y otro para recorrer los destinos. En cada combinación se compara si pasar por el intermediario permite encontrar un tiempo menor.

Por otro lado, una vez finalizado el procesamiento inicial, consultar el tiempo mínimo entre dos usuarios tiene una complejidad de O(1), ya que simplemente se accede a una posición de la matriz. La reconstrucción de la cadena tiene una complejidad de O(n) en el peor caso, porque puede necesitar recorrer varios usuarios hasta llegar al destino.

Por lo tanto, aunque el procesamiento inicial tiene un costo de O(n³), las consultas posteriores son mucho más rápidas, ya que no necesitan volver a ejecutar el algoritmo.

## Funcionalidad B1 - Formación de equipos con restricciones.

### 1. Análisis del problema

La funcionalidad consiste en formar equipos de estudiantes de una red social universitaria, teniendo en cuenta ciertas condiciones y restricciones que deben cumplirse para que un equipo sea considerado válido.

Los datos de entrada van a ser los siguientes: Los estudiantes disponibles para formar los equipos. El identificador, nombre y habilidades de cada estudiante. La cantidad de integrantes que debe tener cada equipo. Habilidades que deben estar cubiertas entre los integrantes. Los estudiantes que obligatoriamente deben participar. Por último, los pares de estudiantes que no pueden formar parte de un mismo equipo.

Las restricciones que encontramos fueron las siguientes: Cada equipo debe tener exactamente la cantidad de integrantes solicitada. Además, entre todos los integrantes deben cubrir las habilidades requeridas. Todos los estudiantes obligatorios deben estar presentes en todos los equipos y los estudiantes incompatibles no pueden formar parte de un mismo equipo.

Luego, un estudiante no puede aparecer más de una vez dentro de un mismo equipo. Por último, no se deben mostrar equipos repetidos que tengan los mismos integrantes en diferente orden.

El objetivo es encontrar y mostrar todos los equipos que cumplan las condiciones establecidas, evitando las combinaciones repetidas. Además, si no es posible formar ningún equipo válido, el sistema deberá informar que no se encontraron soluciones.

### 2. Selección de la estrategia y justificación

La estrategia seleccionada para resolver este problema fue Backtracking (Vuelta Atrás), utilizando el esquema general del algoritmo que vimos en clase.

La razón por la que decidimos utilizar Backtracking es porque necesitamos explorar diferentes combinaciones de estudiantes y verificar cuáles cumplen las restricciones del problema. Esta estrategia nos permite construir una solución de forma progresiva, agregando estudiantes a un equipo y comprobando si la combinación que estamos formando sigue siendo válida. En caso de encontrar una combinación que no cumpla las restricciones, podemos retroceder y probar con otros estudiantes.

Además, Backtracking nos permite encontrar todas las soluciones posibles, que es justamente lo que necesitamos para esta funcionalidad. No buscamos solamente formar un equipo válido, sino encontrar todos los equipos que puedan construirse respetando las condiciones.

Otra característica que consideramos importante para elegir esta estrategia es la posibilidad de aplicar poda de alternativas. Esto significa que podemos descartar una combinación antes de terminar de construirla cuando ya sabemos que no podrá convertirse en un equipo válido.

### 3. Explicación general de la implementación

Para implementar esta funcionalidad, primero creamos una clase Estudiante que permite representar a cada estudiante mediante su identificador, nombre y habilidades. También incorporamos un método para verificar si un estudiante posee una determinada habilidad.

Luego creamos la clase FormadorEquipos, que contiene el algoritmo encargado de encontrar todos los equipos válidos utilizando Backtracking.

Para desarrollar el algoritmo nos basamos en el pseudocódigo general de Backtracking que vimos en clase, donde se trabaja con una solución parcial, se generan posibles candidatos, se comprueba su factibilidad y se retrocede cuando corresponde.

Primero, el algoritmo comienza con un equipo vacío y recorre los estudiantes disponibles para ir agregándolos de a uno. Cada vez que incorpora un estudiante, verifica si la combinación parcial sigue siendo factible.

Esta verificación se realiza mediante el método esFactible, que comprueba que no se supere el tamaño permitido, que no existan estudiantes incompatibles dentro del equipo y que todavía queden lugares suficientes para incorporar a los integrantes obligatorios que faltan.

Si alguna de estas condiciones no se cumple, se descarta esa alternativa y no se continúa explorando esa combinación. De esta manera aplicamos la poda de alternativas que vimos en clase.

En cambio, si la combinación todavía es válida, el algoritmo comprueba mediante el método esSolucion si ya se formó un equipo completo que cumpla todas las condiciones. Para esto verifica que tenga la cantidad exacta de integrantes, que estén presentes todos los estudiantes obligatorios y que entre los integrantes se cubran las habilidades requeridas.

Es importante mencionar que las habilidades se comprueban cuando el equipo está completo, porque un equipo parcial podría no cubrir todas las habilidades pero conseguirlo al incorporar nuevos estudiantes.

Si se encuentra una solución válida, se guarda una copia del equipo en una lista de resultados para poder mostrarlo posteriormente.

Luego, el algoritmo retrocede eliminando al último estudiante incorporado y continúa probando otras alternativas. Este procedimiento se repite hasta explorar todas las combinaciones posibles que no hayan sido descartadas por las restricciones.

Para evitar equipos repetidos, al continuar una búsqueda se consideran solamente los estudiantes que aparecen después del último seleccionado. De esta manera, una combinación no vuelve a generarse únicamente por cambiar el orden de sus integrantes.

Finalmente, desde el menú de consola podemos visualizar los estudiantes y las restricciones configuradas, ingresar la cantidad de integrantes por equipo y ejecutar el algoritmo para mostrar todos los equipos válidos encontrados.

### 4. Otras estrategias consideradas

Una posible estrategia o solución que pensamos para resolverlo era utilizar Greedy seleccionando primero a los estudiantes que cubran la mayor cantidad de habilidades requeridas y luego continuar agregando integrantes hasta completar el equipo. Sin embargo, esta estrategia no resulta buena porque una decisión que parece conveniente en un primer momento puede impedir formar otras combinaciones válidas. Además, nuestro objetivo no es encontrar únicamente un equipo que cumpla las condiciones, sino mostrar todos los equipos posibles. Por lo tanto, utilizar Greedy no nos garantiza encontrar todas las soluciones que necesita esta funcionalidad.

Otra estrategia que analizamos fue Programación Dinámica, que permite resolver problemas utilizando resultados de subproblemas más pequeños y almacenándolos para evitar cálculos repetidos. Si bien podría aplicarse a otros problemas relacionados con la selección de estudiantes, en nuestro caso necesitamos generar equipos concretos y verificar diferentes restricciones entre sus integrantes. Además, debemos mostrar todas las combinaciones válidas y no solamente obtener un valor óptimo. Es por este motivo que consideramos que no sería la alternativa más sencilla para implementar esta funcionalidad.

Es por estas razones que con el equipo decidimos utilizar Backtracking, debido a que permite generar las combinaciones de manera progresiva, comprobar restricciones y encontrar todas las soluciones válidas para cumplir con la funcionalidad.

### 5. Análisis de complejidad temporal

Para analizar la complejidad temporal del algoritmo de Backtracking, vamos a considerar que n representa la cantidad de estudiantes disponibles.

Como el objetivo es formar equipos, el algoritmo debe explorar diferentes combinaciones de estudiantes. En términos generales, cada estudiante puede ser seleccionado o no seleccionado para integrar un equipo.

Esto significa que si no consideramos las restricciones, la cantidad de combinaciones posibles puede crecer exponencialmente a medida que aumenta la cantidad de estudiantes.

Por ejemplo, con 6 estudiantes y equipos de 3 integrantes existen 20 combinaciones diferentes. Si aumentamos la cantidad de estudiantes, también aumenta considerablemente la cantidad de alternativas que tenemos que analizar.

En nuestro algoritmo utilizamos un método recursivo que va incorporando estudiantes y comprobando si la combinación sigue siendo válida. Si la combinación no cumple alguna restricción, se descarta y no se continúa explorando esa rama.

Esta poda permite reducir la cantidad de alternativas que efectivamente se recorren, aunque no garantiza que siempre podamos descartar muchas combinaciones. En el peor caso, cuando las restricciones permiten continuar explorando prácticamente todas las alternativas, la búsqueda puede tener un crecimiento exponencial.

Por lo tanto, la cantidad de combinaciones que puede explorar Backtracking tiene un costo de: O(2^n)

Además, en cada combinación se realizan verificaciones de incompatibilidades, integrantes obligatorios y habilidades requeridas, por lo que el tiempo total de ejecución también depende del costo de estas comprobaciones.

Si consideramos una cantidad fija de restricciones y habilidades, una cota simplificada del tiempo total de nuestra implementación es O(n × 2^n), ya que algunas verificaciones pueden recorrer los integrantes del equipo.

De todas maneras, el aspecto principal de su complejidad es el crecimiento exponencial de las combinaciones posibles. La poda puede mejorar el tiempo de ejecución en muchos casos, pero no modifica ese comportamiento general en el peor caso.

## Funcionalidad B3 - Cadenas alternativas de difusión

### 1. Análisis del problema

Esta funcionalidad consiste en encontrar todas las cadenas de contactos que permitan transmitir una convocatoria desde un usuario de origen hasta otro de destino, respetando determinadas restricciones.

En este caso necesitamos encontrar todas las alternativas válidas y determinar cuál tiene el menor tiempo de transmisión.

Los datos de entrada van a ser:

- Los usuarios y vínculos que forman parte de la red social.
- El tiempo estimado de transmisión de cada vínculo.
- El usuario de origen y el usuario de destino.
- El tiempo máximo permitido para la transmisión.
- La cantidad máxima de usuarios intermediarios.
- Un conjunto de usuarios excluidos.

Las restricciones que encontramos fueron:

- Los vínculos son dirigidos y tienen tiempos positivos.
- El tiempo total de una cadena no puede superar el máximo establecido.
- No se puede superar la cantidad máxima de intermediarios permitidos, sin contar al origen y al destino.
- Los usuarios excluidos no pueden formar parte de las cadenas.
- Un mismo usuario no puede aparecer más de una vez dentro de una cadena.
- Puede ocurrir que no exista ninguna cadena que cumpla las restricciones.

El objetivo es encontrar y mostrar todas las cadenas válidas, indicando los usuarios que las integran y su tiempo total. Además, se debe identificar la cadena más rápida e informar cuando no existan soluciones.

### 2. Selección de la estrategia y justificación

La estrategia seleccionada para resolver esta funcionalidad fue Backtracking. La razón por la que decidimos utilizar esta estrategia es porque el problema requiere explorar diferentes caminos entre dos usuarios para encontrar todas las cadenas que cumplan las condiciones establecidas. Entonces, Backtracking permite construir una solución de manera progresiva. En cada paso se analiza a qué usuarios se puede avanzar desde el usuario actual y se verifica si se cumplen las restricciones. Si el siguiente usuario es válido, se lo incorpora a la cadena y se continúa explorando de forma recursiva.

Cuando se llega al destino, se registra la cadena encontrada. Luego, el algoritmo retrocede para probar otras alternativas que todavía no fueron analizadas.

Una ventaja de esta estrategia es que permite descartar caminos que ya no pueden generar una solución válida. Por ejemplo, si se supera el tiempo máximo o la cantidad de intermediarios permitidos, no tiene sentido continuar explorando esa alternativa.

Por estos motivos consideramos que Backtracking es adecuado, ya que permite encontrar todas las cadenas válidas y evitar continuar con recorridos que incumplen las restricciones.

### 3. Explicación general de la implementación

Para implementar esta funcionalidad reutilizamos la clase Grafo desarrollada para la funcionalidad 2, que representa la red mediante una matriz de adyacencia. En ella se almacenan los tiempos de transmisión y se utiliza -1 cuando no existe un vínculo directo.

Creamos la clase CadenasAlternativas, que recibe un objeto Grafo y utiliza sus métodos para consultar los usuarios, verificar conexiones y obtener los tiempos de transmisión.

El método principal es buscarCadenas, que recibe el origen, el destino, el tiempo máximo, el límite de intermediarios y los usuarios excluidos.

Para realizar la búsqueda utilizamos arreglos de booleanos que permiten identificar los usuarios visitados y excluidos. Además, usamos una lista para almacenar la cadena actual y otra para conservar la mejor cadena encontrada.

El algoritmo comienza desde el usuario de origen y, mediante el método recursivo backtracking, analiza las conexiones disponibles. Antes de avanzar verifica que el siguiente usuario no haya sido visitado, que no esté excluido y que no se superen los límites establecidos.

Si cumple las condiciones, se incorpora a la cadena y continúa la búsqueda. Cuando llega al destino, muestra la cadena, incrementa el contador de soluciones y compara su tiempo con el menor registrado hasta ese momento.

Después de cada llamada recursiva, el algoritmo elimina el último usuario agregado y lo desmarca como visitado. Esto permite retroceder y explorar otras alternativas.

Las cadenas se muestran a medida que se encuentran, por lo que solamente necesitamos conservar la cadena actual y la mejor solución obtenida.

Finalmente, integramos la funcionalidad al menú principal del sistema, utilizando el mismo grafo que Dijkstra y Floyd.

También desarrollamos la clase PruebasCadenasAlternativas, donde utilizamos un grafo independiente con conexiones conocidas para verificar los resultados. Se realizaron 11 casos de prueba que contemplan diferentes restricciones, usuarios excluidos, límites de tiempo y situaciones donde no existen cadenas válidas.

### 4. Otras estrategias seleccionadas

Una de las estrategias que consideramos fue Greedy mediante el algoritmo de Dijkstra, utilizado anteriormente en la funcionalidad 2. Sin embargo, si bien Dijkstra permite encontrar los caminos de menor tiempo desde un usuario de origen, no está diseñado para encontrar todas las cadenas posibles. Como esta funcionalidad requiere mostrar todas las alternativas válidas y no solamente la más rápida, descartamos utilizarlo como solución principal.

También consideramos Programación Dinámica mediante el algoritmo de Floyd. Este permite calcular los caminos mínimos entre todos los pares de usuarios de un grafo. Sin embargo, en nuestro caso solamente necesitamos analizar las cadenas entre un origen y un destino determinados y encontrar todas las alternativas que cumplan las restricciones.

Por último, podríamos utilizar una búsqueda exhaustiva que explore todas las combinaciones y verifique las restricciones al finalizar cada recorrido. Sin embargo, esto implicaría continuar explorando caminos que ya sabemos que no pueden producir una solución válida.

Por estos motivos es que optamos por Backtracking y no por el resto de estrategias.

### 5. Análisis de complejidad temporal

Para analizar la complejidad temporal consideramos que n representa la cantidad de usuarios del grafo.

El algoritmo utiliza llamadas recursivas para explorar las diferentes cadenas posibles. En cada llamada se recorren los n usuarios para verificar cuáles pueden ser el siguiente integrante de la cadena, por lo que este recorrido tiene una complejidad de O(n).

Como no se permite repetir usuarios, en cada nivel de la búsqueda disminuye la cantidad de alternativas disponibles.

En el peor caso, si todos los usuarios están conectados y las restricciones no permiten descartar caminos, el algoritmo puede llegar a explorar una cantidad factorial de recorridos.

Por lo tanto, la complejidad temporal en el peor caso es:

**O(n!)**

Sin embargo, las verificaciones de usuarios visitados, usuarios excluidos, tiempo máximo e intermediarios permiten reducir la cantidad de caminos explorados en muchos casos, aunque no modifican la complejidad del peor caso.

En cuanto a la memoria, los arreglos, las listas y las llamadas recursivas utilizan un espacio adicional de O(n), ya que solamente almacenamos la cadena actual y la mejor encontrada.

La matriz de adyacencia ocupa O(n²), pero pertenece a la clase Grafo que ya utilizamos en las demás funcionalidades y no necesitamos duplicarla.

En conclusión, la complejidad temporal del algoritmo es O(n!) en el peor caso y el espacio adicional utilizado por la búsqueda es O(n). Esta complejidad es aceptable para trabajar con redes pequeñas, teniendo en cuenta que necesitamos encontrar todas las cadenas válidas y no solamente una.

