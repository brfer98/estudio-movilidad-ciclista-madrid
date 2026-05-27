# Estudio de Movilidad Ciclista - Puente de Vallecas

Este proyecto es una aplicación Java desarrollada para analizar el dataset municipal de siniestralidad, con el objetivo de evaluar la seguridad de la movilidad ciclista en el distrito de Puente de Vallecas (Madrid). El diseño está especialmente orientado a identificar zonas de bajo riesgo para el aprendizaje de ciclistas adultos.

## Enfoque Técnico y Arquitectura

A diferencia de los análisis estadísticos tradicionales, el valor principal de este proyecto reside en su arquitectura de software robusta, limpia y mantenible:

* **Arquitectura en 3 Capas:** Desacoplamiento total de responsabilidades:
    * **DAO (Data Access Object):** Lectura eficiente y mapeo del archivo CSV utilizando `java.nio`.
    * **Service (Lógica de Negocio):** El "cerebro" del sistema, donde residen los algoritmos de filtrado y procesamiento.
    * **Presentation:** Capa encargada de la interfaz de consola y el reporte de resultados.
* **Paradigma Funcional (Java Streams):** Procesamiento declarativo de colecciones mediante pipelines de datos. Se sustituyen por completo los bucles tradicionales (`for`/`while`) por operaciones más legibles y optimizadas (`.filter()`, `.map()`, `.collect()`).
* **Inmutabilidad Estricta:** Uso de **Lombok (`@Value`)** para garantizar que los registros de accidentes sean objetos de solo lectura.

## Stack Tecnológico

* **Lenguaje:** Java 21
* **Gestor de Dependencias:** Maven
* **Librerías:** Lombok
* **I/O:** Java NIO (`java.nio.file`)
