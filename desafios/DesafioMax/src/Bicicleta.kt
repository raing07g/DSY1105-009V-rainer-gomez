class Bicicleta(
    val id: String,
    val tipo: String,
    val tarifaHora: Double
) {
    // Encapsulamiento: La disponibilidad solo puede cambiar desde dentro de la lógica del negocio
    var disponible: Boolean = true
        private set

    // Bloque init para el control explícito de errores (reglas de dominio)
    init {
        require(id.isNotBlank()) { "Error: El identificador no puede estar vacío." }
        require(tarifaHora > 0) { "Error: La tarifa por hora debe ser mayor a 0. Tarifa dada: $tarifaHora" }
    }

    fun arrendar() {
        if (!disponible) throw IllegalStateException("La bicicleta $id ya está arrendada.")
        disponible = false
    }

    fun devolver() {
        disponible = true
    }

    override fun toString(): String {
        val estado = if (disponible) "Disponible" else "Arrendada"
        return "Bici[$id] - $tipo - $$tarifaHora/h - $estado"
    }
}