import matrix.calculation.BooleanMatrix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Main {
  public static void main(String[] args) {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    System.out.println("Enter matrix size: ");
    try {
      int size = Integer.parseInt(reader.readLine());
      BooleanMatrix matrix1 = new BooleanMatrix(size);
      matrix1.populateRamdomly();
      System.out.println(matrix1);
      BooleanMatrix matrix2 = new BooleanMatrix(size);
      matrix2.populateRamdomly();
      System.out.println(matrix2);
      LocalDateTime start = LocalDateTime.now();
      BooleanMatrix result = matrix1.multiply(matrix2);
      LocalDateTime end = LocalDateTime.now();
      System.out.println(result);
      System.out.println("Serial: " + ChronoUnit.MILLIS.between(start, end));

      start = LocalDateTime.now();
      BooleanMatrix parallelResult = matrix1.multiplyParallel(matrix2);
      end = LocalDateTime.now();
      System.out.println(parallelResult);
      System.out.println("Parallel: " + ChronoUnit.MILLIS.between(start, end));

      start = LocalDateTime.now();
      BooleanMatrix parallelResult2 = matrix1.multiplyParallel2(matrix2);
      end = LocalDateTime.now();
      System.out.println(parallelResult2);
      System.out.println("Parallel2: " + ChronoUnit.MILLIS.between(start, end));

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

