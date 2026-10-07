# 05. Versión Preliminar del Modelo de Datos

Para dar soporte al flujo de 2 pantallas sin agregar complejidad innecesaria en la etapa de programación, se define un modelo de dominio compuesto por una única entidad principal.

## Diagrama de Entidad

┌────────────────────────────────────────┐
│                PROYECTO                │
├────────────────────────────────────────┤
│ + id : Int [PK]                        │
│ + nombre : String                      │
│ + descripcion : String                 │
│ + area : String                        │
│ + anio : Int                           │
│ + estado : String                      │
│ + imagenUrl : String                   │
│ + contenidoRelacionado : String        │
└────────────────────────────────────────┘

## Atributos de la Entidad

| Atributo | Tipo de Dato | Descripción | Ejemplo |
| :--- | :--- | :--- | :--- |
| `id` | Entero (`Int`) | Identificador único del proyecto (Clave Primaria). | `1` |
| `nombre` | Texto (`String`) | Nombre o título del proyecto. | `"Sistema Constructivo en Madera"` |
| `descripcion` | Texto (`String`) | Descripción detallada de la iniciativa. | `"Desarrollo de módulos prefabricados..."` |
| `area` | Texto (`String`) | Categoría temática usada para el filtrado. | `"Construcción"` |
| `anio` | Entero (`Int`) | Año de desarrollo o ejecución. | `2026` |
| `estado` | Texto (`String`) | Estado actual del proyecto. | `"En desarrollo"` |
| `imagenUrl` | Texto (`String`) | Identificador del recurso de imagen en la app. | `"img_proyecto_1"` |
| `contenidoRelacionado` | Texto (`String`) | Referencia a capacidades o temas vinculados. | `"Capacidad: Ensayos Estructurales"` |