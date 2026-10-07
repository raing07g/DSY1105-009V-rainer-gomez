package cl.duoc.dsy1105.lab;

public class LaboratorioJava {

    public static String obtenerEstadoBateria(int bateria) {

        if (bateria < 20) {
            return "Batería baja";
        } else if (bateria < 80) {
            return "Batería suficiente";
        } else {
            return "Batería alta";
        }
    }

    public static void main(String[] args) {

        String modelo = "Galaxy A55";
        int bateria = 18;
        boolean ahorroEnergia = false;
        String propietario = null;

        System.out.println("=== TELÉFONO ===");
        System.out.println("Modelo: " + modelo);
        System.out.println("Batería: " + bateria + "%");

        String estado = obtenerEstadoBateria(bateria);
        System.out.println("Estado: " + estado);

        System.out.println("Ahorro de energía: " + ahorroEnergia);

        if (propietario == null) {
            System.out.println("Propietario: Sin propietario");
        } else {
            System.out.println("Propietario: " + propietario);
        }
    }
}

