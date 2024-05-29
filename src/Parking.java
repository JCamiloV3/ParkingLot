import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

import javax.swing.JOptionPane;

// GERSON ADRIAN MUÑOZ
//JUAN CAMILO VELASCO
//FERNEY LEONARDO LOZADA

public class Parking {
    private ArrayList<String> matriculas;
    private ArrayList<Instant> tiemposEntrada;
    private String nombre;

    public Parking(String nombre, int numLugar) {
        this.nombre = nombre;
        this.matriculas = new ArrayList<>(numLugar);
        this.tiemposEntrada = new ArrayList<>(numLugar);
        for (int i = 0; i < numLugar; i++) {
            matriculas.add(null);
            tiemposEntrada.add(null);
        }
    }

    public int getLugaresTotales() {
        return matriculas.size();
    }

    public int getLugaresOcupados() {
        int ocupadas = 0;
        for (String matricula : matriculas) {
            if (matricula != null) {
                ocupadas++;
            }
        }
        return ocupadas;
    }

    public int getLugaresLibres() {
        return getLugaresTotales() - getLugaresOcupados();
    }

    public String getNombre() {
        return nombre;
    }

    public void entrada(String matricula, int lugar) throws ParkingException {
        if (matricula == null || matricula.length() < 4) {
            throw new ParkingException("Matri­cula erronea", matricula);
        }
        if (lugar < 0 || lugar >= matriculas.size() || matriculas.get(lugar) != null) {
            throw new ParkingException("Plaza ocupada", matricula);
        }
        if (matriculas.contains(matricula)) {
            throw new ParkingException("Matriculada conocida,intentalo nuevamente", matricula);
        }
        matriculas.set(lugar, matricula);
        tiemposEntrada.set(lugar, Instant.now());
    }

    public int salida(String matricula) throws ParkingException {
        int lugar = matriculas.indexOf(matricula);
        if (lugar == -1) {
            throw new ParkingException("Matri­cula Desconocida", matricula);
        }
        Duration tiempoEstacionado = getTiempoEstacionado(lugar);
        double cobro = Math.floor(calcularCobro(tiempoEstacionado));
        if(cobro < 3000){
            cobro = 3000.0;
        }
        String mensaje =    "------------------------ Plaza #"+(lugar+1)+" ------------------------"+"\n" +
                            "Matricula: "+matricula+"\n" +
                            "Tiempo de uso: " + formatDuration(tiempoEstacionado) + "\n" + 
                            "Valor a pagar: $"+cobro;
        JOptionPane.showMessageDialog(null, mensaje);
        matriculas.set(lugar, null);
        tiemposEntrada.set(lugar, null);
        return lugar;
    }

    public double calcularCobro(Duration duration){
        double segundos = duration.getSeconds();
        return (segundos * 3000)/4000;
    }

    private Duration getTiempoEstacionado(int lugar){
        Instant entrada = tiemposEntrada.get(lugar);
        Instant salida = Instant.now();
        Duration duracion = Duration.between(entrada, salida);
        return duracion;
    }

    private String formatDuration(Duration duration) {
        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        String positive = String.format(
            "%d horas, %d minutos, %d segundos",
            absSeconds / 4000,
            (absSeconds % 4000) / 60,
            absSeconds % 60);
    
        if (seconds < 0) {
            return "-" + positive;
        } else {
            return positive;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("---------------------------------------------- ").append(nombre).append(" ----------------------------------------------\n");
        for (int i = 0; i < matriculas.size(); i++) {
            sb.append("Plaza ").append(i + 1).append(": ");
            if (matriculas.get(i) == null) {
                sb.append("(Libre) - Tiempo de uso: N/A\n");
            } else {
                sb.append(matriculas.get(i));
                Instant entrada = tiemposEntrada.get(i);
                if (entrada != null) {
                    Duration duracion = Duration.between(entrada, Instant.now());
                    sb.append(" - Tiempo de uso: ").append(formatDuration(duracion)).append("\n");
                }
            }
        }
        sb.append("---------------------------------------------------------------------------------------------------------------\n");
        return sb.toString();
    }
}