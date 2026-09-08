import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("=== SISTEMA DE GESTIÓN BIKECITY ===")
    val sistema = SistemaBikeCity()

    val b1 = BiciCiudad("BC12CD", "Trek FX3", TipoCliente.ABONADO)
    val b2 = BiciCiudad("BC99ZA", "Giant Escape", TipoCliente.TURISTA)
    val b3 = BiciMontana("BM22TO", "Scott Aspect", TipoCliente.TURISTA)
    val b4 = BiciElectrica("BE44RG", "Specialized Vado", TipoCliente.DISCAPACITADO, esLargaAutonomia = true)
    val b5 = BiciElectrica("BE77RG", "Trek Allant", TipoCliente.TURISTA, esLargaAutonomia = false)

    println("\n--- prueba 1: código con formato incorrecto ---")
    val biciError = BiciCiudad("123ABC", "Genérica", TipoCliente.TURISTA)
    sistema.registrarEntrada(biciError)

    println("\n--- prueba 2: registrar Entradas ---")
    sistema.registrarEntrada(b1)
    sistema.registrarEntrada(b2)
    sistema.registrarEntrada(b3)
    sistema.registrarEntrada(b4)
    sistema.registrarEntrada(b5)

    println("\n--- prueba 3: registrar salidas ---")
    sistema.registrarSalida("BC12CD", 75)
    sistema.registrarSalida("BC99ZA", 180)
    sistema.registrarSalida("BM22TO", 18)
    sistema.registrarSalida("BE44RG", 120)
    sistema.registrarSalida("BE77RG", 45)

    println("\n--- prueba 4: bicicleta no encontrada ---")
    sistema.registrarSalida("XX99XX", 30)

    sistema.mostrarReporteCierre()
}