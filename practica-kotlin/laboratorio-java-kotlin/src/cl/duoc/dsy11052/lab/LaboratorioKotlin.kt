package cl.duoc.dsy1105.lab

fun obtenerEstadoBateria(bateria: Int): String {

    if (bateria < 20) {
        return "Batería baja"
    } else if (bateria < 80) {
        return "Batería suficiente"
    } else {
        return "Batería alta"
    }
}

fun main() {

    val modelo = "Galaxy A55"
    val bateria = 18
    val ahorroEnergia = false
    val propietario: String? = null

    println("=== TELÉFONO ===")
    println("Modelo: $modelo")
    println("Batería: $bateria%")

    val estado = obtenerEstadoBateria(bateria)
    println("Estado: $estado")

    println("Ahorro de energía: $ahorroEnergia")

    val nombrePropietario = propietario ?: "Sin propietario"
    println("Propietario: $nombrePropietario")
}