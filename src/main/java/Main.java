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
      System.out.println("Matrix A");
      System.out.println(matrix1);
      BooleanMatrix matrix2 = new BooleanMatrix(size);
      matrix2.populateRamdomly();
      System.out.println("Matrix B");
      System.out.println(matrix2);

      System.out.println("- Serial Result -");
      LocalDateTime startSerial = LocalDateTime.now();
      BooleanMatrix result = matrix1.multiply(matrix2);
      LocalDateTime endSerial = LocalDateTime.now();
      System.out.println(result);

      System.out.println("- Parallel Result -");
      LocalDateTime startParallel = LocalDateTime.now();
      BooleanMatrix parallelResult = matrix1.multiplyParallel(matrix2);
      LocalDateTime endParallel = LocalDateTime.now();
      System.out.println(parallelResult);

      System.out.println("- More Parallel Result -");
      LocalDateTime startMoreParallel = LocalDateTime.now();
      BooleanMatrix parallelResult2 = matrix1.multiplyMoreParallel(matrix2);
      LocalDateTime endMoreParallel = LocalDateTime.now();
      System.out.println(parallelResult2);

      System.out.println(
          "Serial: " + ChronoUnit.MILLIS.between(startSerial, endSerial) + " MILLIS");
      System.out.println(
          "Parallel: " + ChronoUnit.MILLIS.between(startParallel, endParallel) + " MILLIS");
      System.out.println(
          "More Parallel: "
              + ChronoUnit.MILLIS.between(startMoreParallel, endMoreParallel)
              + " MILLIS");

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
