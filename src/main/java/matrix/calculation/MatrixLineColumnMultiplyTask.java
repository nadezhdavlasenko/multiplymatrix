package matrix.calculation;

import java.util.concurrent.RecursiveTask;

public class MatrixLineColumnMultiplyTask extends RecursiveTask<Boolean> {

  private static final long serialVersionUID = 4246099615319272220L;

  private boolean[][] matrix1;
  private boolean[][] matrix2;
  private Position position;

  MatrixLineColumnMultiplyTask(boolean[][] matrix1, boolean[][] matrix2, Position position) {
    this.matrix1 = matrix1;
    this.matrix2 = matrix2;
    this.position = position;
  }

  @Override
  protected Boolean compute() {
    return CellCalculator.calculateCell(matrix1, matrix2, position);
  }
}
