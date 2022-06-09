public class Tasas {
    private String csvLine;
    private int minimo;
    private int maximo;
    private Double interes;

    public Tasas() {
    }

    public String getCsvLine() {
        return csvLine;
    }

    public int getMinimo() {
        return minimo;
    }

    public void setMinimo(int minimo) {
        this.minimo = minimo;
    }

    public int getMaximo() {
        return maximo;
    }

    public void setMaximo(int maximo) {
        this.maximo = maximo;
    }

    public Double getInteres() {
        return interes;
    }

    public void setInteres(Double interes) {
        this.interes = interes;
    }

    public void setCsvLine(String csvLine) {
        this.csvLine = csvLine;
    }

    public void parsing() {
        try {
            String[] split = csvLine.split(",");
            minimo = Integer.parseInt(split[0]);
            maximo = Integer.parseInt(split[1]);
            interes = Double.parseDouble(split[2]);
        } catch (Exception e) {
            System.out.println("Error parsingCSV :: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Tasas [interes=" + interes + ", maximo=" + maximo + ", minimo=" + minimo + "]";
    }

}