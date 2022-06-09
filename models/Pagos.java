import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Pagos {

    public Pagos() {
    }

    public Date getDays(String string) {
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

    public void pagosPendientes(String cliente) {
        Date date1 = getDays(Main.fecha);
        AtomicBoolean isUser = new AtomicBoolean(false);
        AtomicBoolean isDebt = new AtomicBoolean(false);
        AtomicInteger count = new AtomicInteger(0);

        Main.prestamosList.forEach(prestamo -> {
            if (prestamo.getCliente().equals(cliente)) {
                isUser.set(true);
                if (prestamo.getEstado().equals("Pendiente")) {
                    isDebt.set(true);
                    count.getAndIncrement();
                    // diferencia de dias
                    System.out.println("\n :: Prestamo #" + count.get() + " ::");
                    Date date2 = getDays(prestamo.getFecha());
                    long diferencia = getDifferenceDays(date1, date2);
                    double tasa = 0;
                    System.out.println("Monto: $" + prestamo.getMonto());
                    System.out.println("Fecha Prestamo: " + prestamo.getFecha());
                    System.out.println("Dias Transcurridos : " + diferencia);
                    for (int i = 0; i < Main.tasasList.size(); i++) {
                        if (diferencia >= Main.tasasList.get(i).getMinimo()
                                && diferencia <= Main.tasasList.get(i).getMaximo()) {
                            tasa = Main.tasasList.get(i).getInteres();
                            break;
                        }
                    }
                    System.out.println("Interes : " + tasa + "%");

                    System.out.println("Monto a pagar : $" + calcularPago(prestamo.getMonto(), diferencia, tasa));

                }
            }
        });

        if (isUser.get() && !isDebt.get()) {
            System.out.println("Este usuario no cuenta con deudas");
        }

    }

    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = 0;
        if (d1.getTime() > d2.getTime()) {
            diff = d1.getTime() - d2.getTime();
        } else {
            diff = d2.getTime() - d1.getTime();
        }
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    private Double calcularPago(Double prestamo, double plazo, double totalInteres) {
        double pago = 0;
        double interes = prestamo * plazo * totalInteres / Main.diasCom;
        interes = (Math.round(interes*100)/100);
        double IVA = interes * Main.iva;
        IVA = (Math.round(IVA*100)/100);
        pago = prestamo + interes + IVA;
        return pago;
    }

}