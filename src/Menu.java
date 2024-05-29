
// GERSON ADRIAN MUÑOZ
//JUAN CAMILO VELASCO
//FERNEY LEONARDO LOZADA

import javax.swing.JOptionPane;

public class Menu {

    private Parking parking;
    private int cantidadPlazas;

    public void agregarCantidadPlazas() {
        do {
            String cantidadPlazasStr = JOptionPane.showInputDialog(null, "Ingrese la cantidad de plazas del parking",
                    "Ajustes", JOptionPane.INFORMATION_MESSAGE);

            this.cantidadPlazas = Integer.parseInt(cantidadPlazasStr);

            if (cantidadPlazas <= 0) {
                JOptionPane.showMessageDialog(null, "Cantidad de plazas no valida. Debe ser mayor a cero", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                this.parking = new Parking("Parking POO Centro", cantidadPlazas);
            }

        } while (cantidadPlazas <= 0);
    }

    public void iniciar() {
        while (true) {
            String menu = "----------------" + parking.getNombre() + "-----------------\n" +
                    "Plazas totales: " + parking.getLugaresTotales() + "\n" +
                    "Plazas ocupados: " + parking.getLugaresOcupados() + "\n" +
                    "Plazas libres: " + parking.getLugaresLibres() + "\n" +
                    "--------------------------------------------\n" +
                    "Menu principal: \n" +
                    "1. Entrada de vehiculo\n" +
                    "2. Salida de vehiculo\n" +
                    "3. Mostrar panel\n" +
                    "4. Salir del programa\n";

            String input = JOptionPane.showInputDialog(null, menu, "Menu principal", JOptionPane.INFORMATION_MESSAGE);

            if (input.equals(null)) {
                JOptionPane.showMessageDialog(null, "Saliendo del programa.");
                break;
            }

            int opcion;

            try {
                opcion = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                if (input.isEmpty()) {
                    continue;
                }
                JOptionPane.showMessageDialog(null, "Opcion incorrecta. Introduzca un numero entre 1 y 4.", "Upss,algo no salio bien :(",
                        JOptionPane.ERROR_MESSAGE);
                continue;
            }

            switch (opcion) {
                case 1:
                    this.opcion1();
                    break;

                case 2:
                    this.opcion2();
                    break;

                case 3:
                    JOptionPane.showMessageDialog(null, parking, "Detalles", JOptionPane.INFORMATION_MESSAGE);
                    break;

                case 4:
                    JOptionPane.showMessageDialog(null, "Saliendo del programa. Vuelve pronto", "Detalles",
                            JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);

                default:
                    JOptionPane.showMessageDialog(null, "Opcion incorrecta. Introduzca un numero entre 1 y 4.");
            }
        }
    }

    private void opcion1() {
        String matriculaEntrada = JOptionPane.showInputDialog("Introduzca la placa del vehiculo:");
        if (matriculaEntrada.equals("")) {
            JOptionPane.showMessageDialog(null, "Matricula erronea", "Upss, algo no salio bien :(", JOptionPane.ERROR_MESSAGE);
        }

        String plazaEntradaStr = JOptionPane.showInputDialog("Introduzca la plaza (1 - " + this.cantidadPlazas + "):");
        int plazaEntrada;
        try {
            plazaEntrada = Integer.parseInt(plazaEntradaStr) - 1;

            if (plazaEntrada < 0) {
                JOptionPane.showMessageDialog(null, "Plaza no valida", "Upss, algo no salio bien :(", JOptionPane.ERROR_MESSAGE);
            } else {
                parking.entrada(matriculaEntrada, plazaEntrada);
                JOptionPane.showMessageDialog(null, "Carro registrado correctamente");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Plaza no valida", "Upss, algo no salio bien :(", JOptionPane.ERROR_MESSAGE);
        } catch (ParkingException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage() + " - Matri­cula: " + e.getMatricula(),
                    "Upss, algo no salio bien :(", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void opcion2() {
        String matriculaSalida = JOptionPane
                .showInputDialog("Introduzca la matri­cula del vehiculo que saldra¡: ");
        try {
            parking.salida(matriculaSalida);
        } catch (ParkingException e) {
            JOptionPane.showMessageDialog(null,
                    "Error: " + e.getMessage() + " - Matri­cula: " + e.getMatricula(), "Upss,algo no salio bien :(",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
