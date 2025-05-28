import java.util.Scanner;

public class RotarIzquierdaArray {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] A = {1, 2, 3, 4, 5};

        System.out.print("Ingrese el número de rotaciones a la izquierda: ");
        int d = scanner.nextInt();

        int[] Aiz = rotarIzquierdaArray(A, d);

        System.out.print("Arreglo rotado a la izquierda: ");
        for (int num : Aiz) {
            System.out.print(num + " ");
        }

        scanner.close();
    }

    public static int[] rotarIzquierdaArray(int[] A, int d) {
        int n = A.length;
        int[] Aiz = new int[n];
        for (int i = 0; i < n; i++) {
            Aiz[i] = A[(i + d) % n];
        }
        return Aiz;
    }
}
