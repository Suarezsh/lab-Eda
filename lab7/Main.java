package btreelab;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int[] tValues = {10, 50, 100};
        String csvFileName = "numeros.csv";
        final int MAX_NUMBERS = 100000;

        List<Double> allNumbers = new ArrayList<>();
        int count = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFileName))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null && count < MAX_NUMBERS) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    try {
                        double value = Double.parseDouble(parts[1]);
                        allNumbers.add(value);
                        count++;
                    } catch (NumberFormatException e) {
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo CSV: " + e.getMessage());
            return;
        }

        if (allNumbers.isEmpty()) {
            System.out.println("No se encontraron números válidos en el archivo CSV.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        double numberToSearch = -1;

        while (true) {
            System.out.print("Introduce el número que deseas buscar: ");
            try {
                String input = scanner.nextLine();
                numberToSearch = Double.parseDouble(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor, introduce un número.");
            }
        }

        System.out.println("\nIniciando búsqueda del número: " + numberToSearch);
        System.out.println("--------------------------------------------------");

        for (int t : tValues) {
            BTree tree = new BTree(t);

            for (double num : allNumbers) {
                tree.insert(num);
            }

            System.out.println("Árbol B construido con t = " + t + " y " + allNumbers.size() + " elementos.");

            long startTimeNs = System.nanoTime();
            BTreeNode resultNode = tree.search(numberToSearch);
            long endTimeNs = System.nanoTime();

            long searchDurationNs = endTimeNs - startTimeNs;

            if (resultNode != null) {
                System.out.printf("Para t = %d: Número %.15f encontrado. Tiempo de búsqueda: %d ns (inicio: %d ns, fin: %d ns)\n",
                                  t, numberToSearch, searchDurationNs, startTimeNs, endTimeNs);
            } else {
                System.out.printf("Para t = %d: Número %.15f NO encontrado. Tiempo de búsqueda: %d ns (inicio: %d ns, fin: %d ns)\n",
                                  t, numberToSearch, searchDurationNs, startTimeNs, endTimeNs);
            }
        }

        System.out.println("--------------------------------------------------");
        System.out.println("Experimento de búsqueda finalizado.");
        scanner.close();
    }
}