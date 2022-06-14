import java.util.ArrayList;
import java.util.List;

public class Prestamo {

    private String cliente;
    private int id;
    private String fecha;
    private Double monto;
    private String estado;
    private String csvLine;

    public Prestamo() {
    }

    public String getCliente() {
        return cliente;
    }

    public int getId() {
        return id;
    }

    public String getFecha() {
        return fecha;
    }

    public Double getMonto() {
        return monto;
    }

    public String getEstado() {
        return estado;
    }

    public String getCsvLine() {
        return csvLine;
    }

    public void setCsvLine(String csvLine) {
        this.csvLine = csvLine;
    }

    public void parsing() {
        try {
            String[] split = csvLine.split(",");
            cliente = split[0].replaceAll("[\\D]", "");
            id = Integer.parseInt(split[1]);
            fecha = split[2];
            monto = Double.parseDouble(split[3]);
            estado = split[4];
        } catch (Exception e) {
            System.out.println("Error parsingCSV :: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Prestamo [cliente=" + cliente + ", estado=" + estado + ", fecha=" + fecha + ", id=" + id + ", monto="
                + monto + "]";
    }

}