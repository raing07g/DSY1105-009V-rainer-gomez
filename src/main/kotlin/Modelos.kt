import java.time.LocalDateTime

enum class TipoCliente {
    TURISTA,
    ABONADO,
    DISCAPACITADO
}

sealed class EstadoSlot {
    object Libre : EstadoSlot()
    data class Arrendada(val bici: Bicicleta) : EstadoSlot()
    data class EnProceso(val mensaje: String) : EstadoSlot()
    data class EnMantencion(val razon: String) : EstadoSlot()
}

open class Bicicleta(
    val codigo: String,
    val marcaModelo: String,
    val tipoCliente: TipoCliente,
    val fechaIngreso: LocalDateTime = LocalDateTime.now()
) {
    open val tarifaBase: Double = 0.0

    open fun calcularCobroBase(minutos: Long): Double {
        val horas = minutos.toDouble() / 60.0
        return horas * tarifaBase
    }

    fun calcularTarifaFinal(minutos: Long): Double {
        val base = calcularCobroBase(minutos)
        if (base <= 0.0) return 0.0

        val conIva = base * 1.19

        return if (tipoCliente == TipoCliente.DISCAPACITADO) {
            conIva * 0.50
        } else {
            conIva
        }
    }
}

class BiciCiudad(
    codigo: String,
    marcaModelo: String,
    tipoCliente: TipoCliente
) : Bicicleta(codigo, marcaModelo, tipoCliente) {
    override val tarifaBase: Double = 800.0

    override fun calcularCobroBase(minutos: Long): Double {
        var horas = minutos.toDouble() / 60.0
        if (tipoCliente == TipoCliente.ABONADO) {
            horas *= 0.80
        }
        return horas * tarifaBase
    }
}

class BiciMontana(
    codigo: String,
    marcaModelo: String,
    tipoCliente: TipoCliente
) : Bicicleta(codigo, marcaModelo, tipoCliente) {
    override val tarifaBase: Double = 1500.0

    override fun calcularCobroBase(minutos: Long): Double {
        if (minutos < 20) {
            return 0.0
        }
        val horas = minutos.toDouble() / 60.0
        return horas * tarifaBase
    }
}

class BiciElectrica(
    codigo: String,
    marcaModelo: String,
    tipoCliente: TipoCliente,
    val esLargaAutonomia: Boolean
) : Bicicleta(codigo, marcaModelo, tipoCliente) {
    override val tarifaBase: Double = 2200.0

    override fun calcularCobroBase(minutos: Long): Double {
        val horas = minutos.toDouble() / 60.0
        var total = horas * tarifaBase
        if (esLargaAutonomia) {
            total *= 1.30
        }
        return total
    }
}

data class Ticket(
    val numero: Int,
    val bici: Bicicleta,
    val minutos: Long,
    val monto: Double
)