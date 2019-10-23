import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

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
      BooleanMatrix result = matrix1.multiply(matrix2);
      System.out.println(result);
      BooleanMatrix parallelResult = matrix1.multiplyParallel(matrix2);
      System.out.println(parallelResult);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

class BooleanMatrix {

  private boolean[][] matrix;

  BooleanMatrix(int size) {
    this.matrix = new boolean[size][size];
  }

  public BooleanMatrix multiply(BooleanMatrix other) {
    BooleanMatrix resultMatrix = new BooleanMatrix(size());
    for (int i = 0; i < size(); i ++) {
      for (int j = 0; j < size(); j ++) {
        boolean sum = false;
        for (int k = 0; k < size(); k++) {
          sum = sum ^ (this.get(i, k) && other.get(k, j));
        }
        resultMatrix.matrix[i][j] =  sum;
      }
    }
    return resultMatrix;
  }

  public BooleanMatrix multiplyParallel(BooleanMatrix other) {
    ForkJoinPool commonPool = ForkJoinPool.commonPool();
    BooleanMatrix resultMatrix = new BooleanMatrix(size());
    MatrixMultiplyRecursiveTask[][] dividedTasks = new MatrixMultiplyRecursiveTask[size()][size()];
    for (int i = 0; i < size(); i ++) {
      for (int j = 0; j < size(); j ++) {
        dividedTasks[i][j] = new MatrixMultiplyRecursiveTask(this.matrix, other.matrix, new Position(i, j));
        commonPool.execute(dividedTasks[i][j]);
      }
    }
    for (int i = 0; i < size(); i ++) {
      for (int j = 0; j < size(); j ++) {
        resultMatrix.matrix[i][j] = dividedTasks[i][j].join();
      }
    }
    return resultMatrix;
  }

  public boolean get(int i, int j) {
    return matrix[i][j];
  }

  public int size() {
    return matrix.length;
  }


  public void populateRamdomly() {
    for (int i = 0; i < size(); i++) {
      for (int j = 0; j < size(); j ++) {
        matrix[i][j] = new Random().nextBoolean();
      }
    }
  }

  @Override
  public String toString() {

    String string = "BooleanMatrix{" + "matrix=\n";

    for (int i = 0; i < size(); i ++ ) {
      string+= Arrays.toString(matrix[i])+ "\n";
    }
    string+= '}';
    return string;
  }
}

class MatrixMultiplyRecursiveTask extends RecursiveTask<Boolean> {
  boolean[][] matrix1;
  boolean[][] matrix2;
  Position position;

  public MatrixMultiplyRecursiveTask(boolean[][] matrix1, boolean[][] matrix2, Position position) {
    this.matrix1 = matrix1;
    this.matrix2 = matrix2;
    this.position = position;
  }

  @Override
  protected Boolean compute() {
    boolean sum = false;
    for (int k = 0; k < matrix1.length; k++) {
      sum = sum ^ (matrix1[position.i][k] && matrix2[k][position.j]);
    }
    return sum;
  }

}

class Position {
  int i;
  int j;

  public Position(int i, int j) {
    this.i = i;
    this.j = j;
  }
}
