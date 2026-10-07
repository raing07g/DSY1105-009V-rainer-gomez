# 03. Requerimientos Funcionales y No Funcionales

## 1. Requerimientos Funcionales (RF)

| ID | Requerimiento Funcional | Trazabilidad |
| :--- | :--- | :--- |
| **RF-01** | El sistema deberá desplegar un listado de proyectos en la pantalla principal. | RAN-01 |
| **RF-02** | Cada elemento del listado deberá mostrar imagen, nombre, área y año del proyecto. | RAN-01 |
| **RF-03** | El sistema deberá filtrar el listado en tiempo real según el texto ingresado en la barra de búsqueda. | RAN-02 |
| **RF-04** | El sistema deberá filtrar los proyectos según el área temática seleccionada en el menú desplegable. | RAN-02 |
| **RF-05** | El sistema deberá permitir seleccionar un proyecto del listado para ver su detalle. | RAN-03, RAN-04 |
| **RF-06** | La pantalla de detalle deberá mostrar: nombre, área, año, estado, descripción completa y contenido relacionado. | RAN-03 |
| **RF-07** | El sistema deberá incluir un botón para regresar desde el detalle al listado principal. | RAN-04 |
| **RF-08** | El sistema deberá mostrar un mensaje informativo cuando una búsqueda no arroje resultados. | RAN-02 |

## 2. Requerimientos No Funcionales (RNF)

| ID | Requerimiento No Funcional | Categoría |
| :--- | :--- | :--- |
| **RNF-01** | La aplicación deberá estar optimizada para dispositivos Android (API 26 / Android 8.0 o superior). | Compatibilidad |
| **RNF-02** | La interfaz gráfica deberá estar diseñada para usarse en orientación vertical (*portrait*). | Usabilidad |
| **RNF-03** | La transición entre el listado y el detalle no deberá tomar más de 1 segundo. | Rendimiento |
| **RNF-04** | La tipografía y contraste de colores deberán asegurar una correcta lectura en pantallas móviles. | Accesibilidad |
| **RNF-05** | La aplicación utilizará datos sintéticos almacenados localmente, sin requerir conexión a red. | Arquitectura |
| **RNF-06** | La interfaz deberá mantener consistencia con los colores corporativos del CIM UC. | Diseño / UI |