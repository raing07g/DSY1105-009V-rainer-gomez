import kotlinx.coroutines.delay

class Slot(val numero: Int) {
    var estado: EstadoSlot = EstadoSlot.Libre
}

class SistemaBikeCity {
    val slots = MutableList(10) { i -> Slot(i + 1) }
    val historialTickets = mutableListOf<Ticket>()
    var contadorTickets = 1

    fun validarCodigo(codigo: String): Boolean {
        val regex = Regex("^[A-Z]{2}\\d{2}[A-Z]{2}$")
        return regex.matches(codigo)
    }

    suspend fun registrarEntrada(bici: Bicicleta) {
        // Validación de código (Imprime aviso y usa return para NO detener la app)
        if (!validarCodigo(bici.codigo)) {
            println("Error: El código '${bici.codigo}' no cumple con el formato válido.")
            return
        }

        var slotLibre: Slot? = null
        for (s in slots) {
            if (s.estado is EstadoSlot.Libre) {
                slotLibre = s
                break
            }
        }

        if (slotLibre == null) {
            println("Error: No hay slots disponibles en la estación.")
            return
        }

        slotLibre.estado = EstadoSlot.EnProceso("Registrando entrada en sensor...")
        println("Slot ${slotLibre.numero}: Conectando con sensor (3 segundos)...")

        delay(3000)

        slotLibre.estado = EstadoSlot.Arrendada(bici)
        println("OK: Bicicleta ${bici.codigo} ingresada en el Slot ${slotLibre.numero}.")
    }

    suspend fun registrarSalida(codigo: String, minutosUso: Long) {
        var slotEncontrado: Slot? = null
        var biciEncontrada: Bicicleta? = null

        for (s in slots) {
            val est = s.estado
            if (est is EstadoSlot.Arrendada && est.bici.codigo == codigo) {
                slotEncontrado = s
                biciEncontrada = est.bici
                break
            }
        }

        if (slotEncontrado == null || biciEncontrada == null) {
            println("Error: La bicicleta '$codigo' no fue encontrada.")
            return
        }

        slotEncontrado.estado = EstadoSlot.EnProceso("Procesando pago y salida...")
        println("Slot ${slotEncontrado.numero}: Procesando salida de $codigo (6.5 segundos)...")

        delay(6500)

        val totalPagar = biciEncontrada.calcularTarifaFinal(minutosUso)

        if (totalPagar < 0) {
            println("Error: El cálculo arroja un monto inválido.")
            slotEncontrado.estado = EstadoSlot.Arrendada(biciEncontrada)
            return
        }

        val ticket = Ticket(contadorTickets++, biciEncontrada, minutosUso, totalPagar)
        historialTickets.add(ticket)

        slotEncontrado.estado = EstadoSlot.Libre
        println("OK: Salida completada. Ticket #${ticket.numero} | Total: $${totalPagar.toInt()} | Slot ${slotEncontrado.numero} libre.")
    }

    fun contarSlotsDisponibles(): Int {
        return slots.count { it.estado is EstadoSlot.Libre }
    }

    fun obtenerPromedioIngresos(): Double {
        if (historialTickets.isEmpty()) return 0.0
        val suma = historialTickets.sumOf { it.monto }
        return suma / historialTickets.size
    }

    fun mostrarReporteCierre() {
        println("\n--------------------------------------------------")
        println("             REPORTE DE CIERRE DE TURNO           ")
        println("--------------------------------------------------")

        if (historialTickets.isEmpty()) {
            println("No se registraron atenciones en este turno.")
        } else {
            for (t in historialTickets) {
                println("Ticket #${t.numero} | Código: ${t.bici.codigo} | Tiempo: ${t.minutos} min | Pagado: $${t.monto.toInt()}")
            }
        }

        println("--------------------------------------------------")
        println("Total Atenciones: ${historialTickets.size}")
        println("Ingreso Promedio: $${obtenerPromedioIngresos().toInt()}")
        println("Slots Libres al Cierre: ${contarSlotsDisponibles()}")
        println("--------------------------------------------------\n")
    }
}