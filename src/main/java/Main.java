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

      LocalDateTime startSerial = LocalDateTime.now();
      BooleanMatrix result = matrix1.multiply(matrix2);
      LocalDateTime endSerial = LocalDateTime.now();
      System.out.println(result);

      LocalDateTime startParallel = LocalDateTime.now();
      BooleanMatrix parallelResult = matrix1.multiplyParallel(matrix2);
      LocalDateTime endParallel = LocalDateTime.now();
      System.out.println(parallelResult);

      LocalDateTime startMoreParallel = LocalDateTime.now();
      BooleanMatrix parallelResult2 = matrix1.multiplyParallel2(matrix2);
      LocalDateTime endMoreParallel = LocalDateTime.now();
      System.out.println(parallelResult2);

      System.out.println("Serial: " + ChronoUnit.MILLIS.between(startSerial, endSerial));
      System.out.println("Parallel: " + ChronoUnit.MILLIS.between(startParallel, endParallel));
      System.out.println("More Parallel: " + ChronoUnit.MILLIS.between(startMoreParallel, endMoreParallel));

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

