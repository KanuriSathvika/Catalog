package practice;

import java.util.HashMap;
import java.util.Scanner;

public class secret {

    // Method to perform Gaussian Elimination
    public static double[] gaussianElimination(double[][] matrix, double[] result) {
        int n = result.length;

        // Forward Elimination
        for (int i = 0; i < n; i++) {
            // Partial pivoting
            for (int k = i + 1; k < n; k++) {
                if (Math.abs(matrix[i][i]) < Math.abs(matrix[k][i])) {
                    // Swap the rows
                    double[] temp = matrix[i];
                    matrix[i] = matrix[k];
                    matrix[k] = temp;

                    // Swap the result values
                    double t = result[i];
                    result[i] = result[k];
                    result[k] = t;
                }
            }

            // Make all rows below this one 0 in the current column
            for (int k = i + 1; k < n; k++) {
                double factor = matrix[k][i] / matrix[i][i];

                // Subtract the row from the current row
                for (int j = i; j < n; j++) {
                    matrix[k][j] -= factor * matrix[i][j];
                }
                result[k] -= factor * result[i];
            }
        }

        // Backward substitution
        double[] solution = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            solution[i] = result[i];
            for (int j = i + 1; j < n; j++) {
                solution[i] -= matrix[i][j] * solution[j];
            }
            solution[i] /= matrix[i][i];
        }

        return solution;
    }

    public static long convertToDecimal(String value, int base) {
        return Long.parseLong(value, base);  // Convert the given value in a specific base to decimal
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the n: ");
        int n = scanner.nextInt(); // total number of points
        System.out.println("Enter value of k: ");
        int k = scanner.nextInt();
        // Simulating JSON-like input data with n points and base encoded values
        HashMap<Integer, String[]> data = new HashMap<>();



        // Take n points input from the user
        for (int i = 1; i <= n; i++) {
            System.out.println("Enter details for point " + i + ":");
            int x=scanner.nextInt();
            System.out.print("Base: ");
            String base = scanner.next();
            System.out.print("Value: ");
            String value = scanner.next();
            data.put(x, new String[]{base, value});
        }
      

        // Matrix size will be k x k for k points
        double[][] matrix = new double[k][k];
        double[] result = new double[k];

        System.out.println("Processing " + k + " points to find polynomial coefficients:");

        // Loop to populate the matrix and result vector with first k points
        for (int i = 1; i <= k; i++) {
            int x = i;  // The x-values are the keys (1, 2, 3, ... k)
            String base = data.get(i)[0];
            String value = data.get(i)[1];

            long y = convertToDecimal(value, Integer.parseInt(base));  // Convert the y value from the given base to decimal

            System.out.println("Point (" + x + ", " + y + ") with base " + base);

            // Fill the matrix for x^2, x, and 1 (quadratic equation form: ax^2 + bx + c = y)
            for (int j = 0; j < k; j++) {
                matrix[i - 1][j] = Math.pow(x, k - j - 1);  // Example: for k = 3, first row will have x^2, x^1, and x^0 (which is 1)
            }
            result[i - 1] = y;  // Fill the corresponding y value
        }

        // Perform Gaussian Elimination to solve for coefficients
        double[] solution = gaussianElimination(matrix, result);

        // Output the solution: Polynomial coefficients
        System.out.print("The polynomial is: f(x) = ");
        for (int i = 0; i < solution.length; i++) {
            if (i > 0) {
                System.out.print(" + ");
            }
            System.out.print(solution[i] + "x^" + (solution.length - i - 1));
        }

        // Output the constant term (c), which is the last term in the solution
        System.out.println("\nThe constant term (c) is: " + solution[solution.length - 1]);
    }
}
