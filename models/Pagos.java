import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Pagos {

    public Pagos() {
    }

    public void pagosPendientes(String cliente) {
        // varificamos sus prestamos
        AtomicBoolean isDebt = new AtomicBoolean(false);
        Main.prestamosList.stream().filter(user -> user.getCliente().equals(cliente))
                .filter(status -> status.getEstado().equalsIgnoreCase("pendiente"))
                .forEach(item -> {
                    isDebt.set(true);
                    System.out.println("Deuda total: $" + calcularPago(item) + "\n");
                });
        // validamos si hubo datos
        if (!isDebt.get()) {
            System.out.println("Este usuario no tiene deudas");
        }
    }

    private Double calcularPago(Prestamo prestamo) {
        Double monto, totalInteres = monto = 0.0;
        monto = prestamo.getMonto();
        long plazo = getDifferenceDays(prestamo.getFecha());
        totalInteres = getInteres(plazo);
        double interes = (monto * plazo * totalInteres) / Main.diasCom;
        System.out.println("Prestamo: $" + monto);
        System.out.println("Dias transcurridos: " + plazo);
        System.out.println("Interes " + totalInteres + "%");
        double IVA = interes * Main.iva;
        double total = monto + interes + IVA;
        total = (Math.round(total * 100) / 100);
        return total;
    }

    private long getDifferenceDays(String date) {
        Date d1 = getDays(Main.fecha);
        Date d2 = getDays(date);
        long diff = 0;
        if (d1.getTime() > d2.getTime()) {
            diff = d1.getTime() - d2.getTime();
        } else {
            diff = d2.getTime() - d1.getTime();
        }
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    private Date getDays(String string) {
        Date date = null;
        try {
            Locale locale = new Locale("es", "Es");
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", locale);
            date = formatter.parse(string);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return date;
    }

    private double getInteres(double diferencia) {
        for (Tasas interes : Main.tasasList) {
            if (diferencia >= interes.getMinimo() && diferencia <= interes.getMaximo()) {
                return interes.getInteres();
            }
        }
        return 0;
    }

}