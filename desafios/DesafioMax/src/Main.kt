fun main() {
    val centro = CentroArriendo()
    var salir = false

    centro.registrarBicicleta("B01", "montaña", 5000.0)
    centro.registrarBicicleta("B02", "paseo", 3000.0)

    while (!salir) {
        println("\n=== MENÚ CENTRO DE ARRIENDO ===")
        println("1. registrar bicicleta")
        println("2. buscar bicicleta")
        println("3. arrendar bicicleta")
        println("4. devolver bicicleta")
        println("5. listar bicicletas (disponibles y ocupadas)")
        println("6. mostrar resumen final")
        println("7. salir")
        print("selecciona una opción: ")

        when (readlnOrNull()) {
            "1" -> {
                print("ID: ")
                val id = readln()
                print("tipo (ej. Montaña): ")
                val tipo = readln()
                print("tarifa por hora: ")
                val tarifa = readln().toDoubleOrNull() ?: 0.0
                centro.registrarBicicleta(id, tipo, tarifa)
            }
            "2" -> {
                print("ingresa el ID a buscar: ")
                val id = readln()
                val bici = centro.buscarBicicleta(id)
                if (bici != null) println(" encontrada: $bici") else println(" bicicleta no encontrada.")
            }
            "3" -> {
                print("ID de la bicicleta a arrendar: ")
                val id = readln()
                print("cantidad de horas: ")
                val horas = readln().toIntOrNull() ?: 0
                centro.arrendar(id, horas)
            }
            "4" -> {
                print("ID de la bicicleta a devolver: ")
                val id = readln()
                centro.devolverBicicleta(id)
            }
            "5" -> {
                centro.listarPorDisponibilidad(true)
                centro.listarPorDisponibilidad(false)
            }
            "6" -> centro.mostrarResumen()
            "7" -> {
                println("cerrando el sistema...")
                salir = true
            }
            else -> println(" opción inválida. intenta nuevamente.")
        }
    }
}