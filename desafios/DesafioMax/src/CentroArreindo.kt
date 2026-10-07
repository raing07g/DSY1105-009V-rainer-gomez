class CentroArriendo {
    private val inventario = mutableMapOf<String, Bicicleta>()

    private var ingresoTotal: Double = 0.0
    private var totalArriendos: Int = 0

    fun registrarBicicleta(id: String, tipo: String, tarifaHora: Double) {
        try {
            if (inventario.containsKey(id)) {
                println("Aviso: Ya existe una bicicleta con el ID $id.")
                return
            }
            val nuevaBici = Bicicleta(id, tipo, tarifaHora)
            inventario[id] = nuevaBici
            println("✅ Registrada: ${nuevaBici.id}")
        } catch (e: IllegalArgumentException) {
            println("❌ Rechazada: ${e.message}")
        }
    }

    fun buscarBicicleta(id: String): Bicicleta? {
        return inventario[id]
    }

    fun arrendar(id: String, horas: Int) {
        val bici = buscarBicicleta(id)

        if (bici == null) {
            println("❌ Error: La bicicleta $id no existe en el sistema.")
            return
        }

        try {
            bici.arrendar() // Intenta cambiar el estado
            val costo = bici.tarifaHora * horas
            ingresoTotal += costo
            totalArriendos++
            println("🚲 Arriendo exitoso: Bici $id por $horas horas. Costo: $$costo")
        } catch (e: IllegalStateException) {
            println("⚠️ No se puede arrendar: ${e.message}")
        }
    }

    fun devolverBicicleta(id: String) {
        val bici = buscarBicicleta(id)
        if (bici != null && !bici.disponible) {
            bici.devolver()
            println("🔄 Devolución exitosa: La bicicleta $id vuelve a estar disponible.")
        } else {
            println("⚠️ Error en devolución: Bici $id no existe o ya estaba disponible.")
        }
    }

    fun listarPorDisponibilidad(disponible: Boolean) {
        val titulo = if (disponible) "DISPONIBLES" else "NO DISPONIBLES"
        println("\n--- BICICLETAS $titulo ---")
        val filtradas = inventario.values.filter { it.disponible == disponible }

        if (filtradas.isEmpty()) {
            println("No hay bicicletas en este estado.")
        } else {
            filtradas.forEach { println(it) }
        }
    }

    fun mostrarResumen() {
        println("\n=== RESUMEN FINAL DEL CENTRO ===")
        println("Total de arriendos procesados: $totalArriendos")
        println("Ingreso total generado: $$ingresoTotal")

        val tarifaPromedio = if (inventario.isNotEmpty()) {
            inventario.values.sumOf { it.tarifaHora } / inventario.size
        } else 0.0
        println("Tarifa promedio del inventario: $$tarifaPromedio")
        println("================================")
    }
}