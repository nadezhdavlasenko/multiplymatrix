package matrix.calculation;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class BooleanMatrix {

  private boolean[][] matrix;

  public BooleanMatrix(int size) {
    this.matrix = new boolean[size][size];
  }

  public BooleanMatrix multiply(BooleanMatrix other) {
    BooleanMatrix resultMatrix = new BooleanMatrix(size());
    for (int i = 0; i < size(); i++) {
      for (int j = 0; j < size(); j++) {
        boolean sum = false;
        for (int k = 0; k < size(); k++) {
          sum = sum ^ (this.matrix[i][k] && other.matrix[k][j]);
        }
        resultMatrix.matrix[i][j] = sum;
      }
    }
    return resultMatrix;
  }

  public BooleanMatrix multiplyParallel(BooleanMatrix other) {
    ForkJoinPool commonPool = ForkJoinPool.commonPool();
    BooleanMatrix resultMatrix = new BooleanMatrix(size());
    MatrixLineColumnMultiplyTask[][] dividedTasks =
        new MatrixLineColumnMultiplyTask[size()][size()];
    for (int i = 0; i < size(); i++) {
      for (int j = 0; j < size(); j++) {
        dividedTasks[i][j] =
            new MatrixLineColumnMultiplyTask(this.matrix, other.matrix, new Position(i, j));
        commonPool.execute(dividedTasks[i][j]);
      }
    }
    for (int i = 0; i < size(); i++) {
      for (int j = 0; j < size(); j++) {
        resultMatrix.matrix[i][j] = dividedTasks[i][j].join();
      }
    }
    return resultMatrix;
  }

  public BooleanMatrix multiplyParallel2(BooleanMatrix other) {
    ForkJoinPool commonPool = ForkJoinPool.commonPool();
    BooleanMatrix resultMatrix = new BooleanMatrix(size());
    resultMatrix.matrix =
        commonPool.invoke(
            new MatrixMultiplyDividerTask(this.matrix, other.matrix, 0, matrix.length));
    return resultMatrix;
  }

  public int size() {
    return matrix.length;
  }

  public void populateRamdomly() {
    for (int i = 0; i < size(); i++) {
      for (int j = 0; j < size(); j++) {
        matrix[i][j] = new Random().nextBoolean();
      }
    }
  }

  @Override
  public String toString() {

    String string = "BooleanMatrix{" + "\n";

    for (int i = 0; i < size(); i++) {
      for (int j = 0; j < size(); j++) {
        string += matrix[i][j] ? "1," : "0,";
      }
      string += "\n";
    }
    string += '}';
    return string;
  }
}
