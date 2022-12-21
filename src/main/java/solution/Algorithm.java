package solution;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Algorithm {
    private static double CC(int i, int j, double prefix[], double squaredSums[]) {
        int lenght = j - i + 1;
        double ijMiu = (prefix[j] - prefix[i - 1]) / lenght;
        double firstArg = lenght * ijMiu * ijMiu;
        double secondArg = ijMiu * (prefix[j] - prefix[i - 1]);
        double thirdArg = (squaredSums[j] - squaredSums[i - 1]);
        return firstArg - 2 * secondArg + thirdArg;
    }

    public static void main(String[] args) {
        File myObj = new File("k-means.in");
        Scanner myReader = null;
        try {
            myReader = new Scanner(myObj);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            if (myReader != null)
                myReader.close();
        }
        int n = myReader.nextInt();
        int k = myReader.nextInt();
        double[] instances = new double[n + 1];
        double[] prefixSums = new double[n + 1];
        double[] squaredSums = new double[n + 1];
        double[] means = new double[n + 1];
        double[][] dp = new double[n + 1][n + 1];

        for (int i = 1; i <= n; i++) {
            instances[i] = myReader.nextDouble();
            prefixSums[i] = prefixSums[i - 1] + instances[i];
            means[i] = (instances[i] + (i - 1) * means[i - 1]) / i;
            squaredSums[i] = squaredSums[i - 1] + instances[i] * instances[i];
        }
        for (int i = 1; i <= n; i++) {
            double sum = 0;
            for (int j = 1; j <= i ; j++)
                sum += (means[i] - instances[j]) * (means[i] - instances[j]);
            dp[1][i] = sum;
        }

        for (int i = 2; i <= k; i++) {
            for (int m = i; m <= n; m++) {
                double minValue = Double.MAX_VALUE;
                for (int j = 1; j <= m; j++) {
                    double newValue = dp[i - 1][j - 1] + CC(j, m, prefixSums, squaredSums);
                    minValue = Math.min(minValue, newValue);
                }
                dp[i][m] = minValue;
            }
        }
        for (int i = 1; i <= k; i++) {
            for (int j = 1; j <= n; j++) {
                System.out.print(dp[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println("Minimum J Criteria value is: " + dp[k][n]);
    }
}
