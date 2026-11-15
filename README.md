# Sorteo de Liga Profesional

Aplicación de consola en Java para gestionar sorteos de eliminación directa en una liga de fútbol profesional (16 equipos). Implementa arquitectura Modelo-Vista-Controlador (MVC), algoritmos recursivos, excepciones personalizadas y serialización de objetos para persistencia de datos.

## Funcionalidades Principales

* **Sorteo aleatorio y recursivo:** Genera los enfrentamientos de cada etapa utilizando algoritmos recursivos.
* **Validaciones:** Verifica que la cantidad de participantes sea potencia de 2 y evita equipos duplicados o nulos.
* **Gestión de etapas:** Organiza octavos, cuartos, semifinales y final, permitiendo registrar los ganadores de cada fase.
* **Manejo de excepciones:** Controla situaciones inválidas mediante `NumeroEquiposInvalidoException` y `SorteoInvalidoException`.
* **Persistencia:** Guarda y recupera el estado completo del torneo en un archivo binario (`.ser`) mediante serialización.

## Arquitectura del Proyecto (MVC)

| Paquete | Clases | Responsabilidad |
| :--- | :--- | :--- |
| **modelo** | `Equipo`, `Partido`, `Torneo` | Lógica de negocio, gestión de equipos, partidos y persistencia. |
| **vista** | `VistaTorneo` | Interfaz de consola para lectura de entradas y despliegue de datos. |
| **controlador** | `ControladorTorneo` | Coordinación del flujo entre el modelo y la vista. |
| **excepciones** | `NumeroEquiposInvalidoException`, `SorteoInvalidoException` | Manejo de errores específicos del sistema. |
| **aplicacion** | `Main` | Punto de entrada de la aplicación. |

## Etapas del Torneo

1. **Octavos de final:** 16 equipos / 8 partidos
2. **Cuartos de final:** 8 equipos / 4 partidos
3. **Semifinales:** 4 equipos / 2 partidos
4. **Final:** 2 equipos / 1 partido

## Menú Principal

* `1`: Realizar sorteo de octavos de final.
* `2`: Registrar ganadores de octavos y sortear cuartos.
* `3`: Registrar ganadores de cuartos y sortear semifinales.
* `4`: Registrar ganadores de semifinales y sortear la final.
* `5`: Mostrar resultados de todas las etapas.
* `6`: Guardar el torneo.
* `7`: Cargar un torneo guardado.
* `0`: Salir.

## Instrucciones de Ejecución

1. Clonar o descargar este repositorio.
2. Abrir **Eclipse IDE**.
3. Importar mediante **File > Import > General > Projects from Folder or Archive**.
4. Seleccionar la carpeta raíz del proyecto.
5. Ejecutar la clase `Main.java` dentro del paquete `aplicacion` (`Run As > Java Application`).

