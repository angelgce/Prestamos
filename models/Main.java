import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.ini4j.Ini;

public class Main {

    public static List<Prestamo> prestamosList = new ArrayList<Prestamo>();
    public static List<Tasas> tasasList = new ArrayList<Tasas>();
    public static String fecha = "";
    public static double iva = 0;
    public static int diasCom = 0;

    private static Config config;

    public static void main(String[] args) {

        System.out.println("Starting ...");
        config = Config.getInstance(args);
        Ini.Section section = Config.ini.get("SYSTEM");
        fecha = section.get("fecha", String.class);
        iva = section.get("iva", Double.class);
        diasCom = section.get("diasComer", Integer.class);

        loadPrestamos();
        lodadTasas();
        HashMap<String, String> clientes = new HashMap<>();
        prestamosList.forEach(item -> {
            if (!clientes.containsKey(item.getCliente())) {
                clientes.put(item.getCliente(), "");
            }
        });
        System.out.println("==== Clientes ===");
        String getScann = "";
        clientes.forEach((k, v) -> System.out.println(k));
        System.out.println("");
        System.out.println("| Fecha Actual: " + Main.fecha + " |");
        while (!getScann.equals("exit")) {
            System.out.println("\n--------------------------------------");
            System.out.println("Por favor escriba el cliente a buscar :: ");
            Scanner sc = new Scanner(System.in);
            getScann = sc.nextLine();
            if (!getScann.equalsIgnoreCase("exit")) {
                System.out.println("\n> Informacion del usuario :: " + getScann);
                Pagos pago = new Pagos();
                pago.pagosPendientes(getScann);
            } else {
                System.out.println("Salio exitosamente...");
            }
            System.out.println("--------------------------------------");

        }

    }

    private static void loadPrestamos() {

        Ini.Section section = Config.ini.get("CSV");
        String path = section.get("prestamos", String.class);
        prestamosList = new ArrayList<Prestamo>();
        Scanner sc = null;
        try {
            sc = new Scanner(new File(path));
            while (sc.hasNext()) {
                // POJO - Tokens
                Prestamo prestamo = new Prestamo();
                prestamo.setCsvLine(sc.nextLine());
                prestamo.parsing();
                prestamosList.add(prestamo);
            }
        } catch (Exception e) {
            System.out.println("error scanning :: " + e.getMessage());
        } finally {
            sc.close();
        }

    }

    private static void lodadTasas() {

        Ini.Section section = Config.ini.get("CSV");
        String path = section.get("tasas", String.class);
        tasasList = new ArrayList<Tasas>();
        Scanner sc = null;
        try {
            sc = new Scanner(new File(path));
            while (sc.hasNext()) {
                // POJO - Tokens
                Tasas tasa = new Tasas();
                tasa.setCsvLine(sc.nextLine());
                tasa.parsing();
                tasasList.add(tasa);
            }
        } catch (Exception e) {
            System.out.println("error scanning :: " + e.getMessage());
        } finally {
            sc.close();
        }

    }

}