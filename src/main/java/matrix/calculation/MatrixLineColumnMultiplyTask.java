package matrix.calculation;

import java.util.concurrent.RecursiveTask;

public class MatrixLineColumnMultiplyTask extends RecursiveTask<Boolean> {

  private static final long serialVersionUID = 4246099615319272220L;

  private boolean[][] matrix1;
  private boolean[][] matrix2;
  private Position position;

  public MatrixLineColumnMultiplyTask(boolean[][] matrix1, boolean[][] matrix2, Position position) {
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
