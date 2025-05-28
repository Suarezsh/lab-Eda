import java.util.Scanner;

public class InvertirArray {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese la cantidad de elementos del arreglo: ");
        int n = scanner.nextInt();
        int[] A = new int[n];

        System.out.println("Ingrese los elementos del arreglo:");
        for (int i = 0; i < n; i++) {
            A[i] = scanner.nextInt();
        }

        int[] Ain = invertirArray(A);

        System.out.print("Arreglo invertido: ");
        for (int num : Ain) {
            System.out.print(num + " ");
        }

        scanner.close();
    }

    public static int[] invertirArray(int[] A) {
        int[] Ain = new int[A.length];
        for (int i = 0; i < A.length; i++) {
            Ain[i] = A[A.length - 1 - i];
        }
        return Ain;
    }
}
