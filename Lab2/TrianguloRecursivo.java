import java.util.Scanner;

public class TrianguloRecursivo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese la base del triángulo: ");
        int base = scanner.nextInt();

        trianguloRecursivo(base);

        scanner.close();
    }

    public static void trianguloRecursivo(int base) {
        imprimirLinea(1, base);
    }

    private static void imprimirLinea(int actual, int base) {
        if (actual > base) return;

        for (int j = 0; j < actual; j++) {
            System.out.print("*");
        }
        System.out.println();
        imprimirLinea(actual + 1, base);
    }
}
