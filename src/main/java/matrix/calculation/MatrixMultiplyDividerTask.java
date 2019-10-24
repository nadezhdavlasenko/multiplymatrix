package matrix.calculation;

import java.util.concurrent.RecursiveTask;

public class MatrixMultiplyDividerTask extends RecursiveTask<boolean[][]> {
  private static final long serialVersionUID = -671929630868022196L;
  private boolean[][] matrix1;
  private boolean[][] matrix2;
  private int startLine;
  private int endLine;
  private boolean[][] resultMatrix;

  MatrixMultiplyDividerTask(boolean[][] matrix1, boolean[][] matrix2, int startLine, int endLine) {
    this.matrix1 = matrix1;
    this.matrix2 = matrix2;
    this.startLine = startLine;
    this.endLine = endLine;
    resultMatrix = new boolean[matrix1.length][matrix1.length];
  }

  @Override
  protected boolean[][] compute() {
    if (endLine - startLine <= 2) {
      return computeValues();
    } else {
      return divideForComputing();
    }
  }

  private boolean[][] computeValues() {
    MatrixLineColumnMultiplyTask[][] dividedTasks =
        new MatrixLineColumnMultiplyTask[matrix1.length][matrix1[0].length];
    for (int i = startLine; i < endLine; i++) {
      for (int j = 0; j < matrix2.length; j++) {
        dividedTasks[i][j] = new MatrixLineColumnMultiplyTask(matrix1, matrix2, new Position(i, j));
        dividedTasks[i][j].fork();
      }
    }
    for (int i = startLine; i < endLine; i++) {
      for (int j = 0; j < resultMatrix.length; j++) {
        resultMatrix[i][j] = dividedTasks[i][j].join();
      }
    }
    return resultMatrix;
  }

  private boolean[][] divideForComputing() {
    int middle = startLine + (endLine - startLine) / 2 + 1;
    MatrixMultiplyDividerTask top =
        new MatrixMultiplyDividerTask(matrix1, matrix2, startLine, middle);
    top.fork();
    MatrixMultiplyDividerTask bottom =
        new MatrixMultiplyDividerTask(matrix1, matrix2, middle, endLine);
    bottom.fork();

    System.arraycopy(top.invoke(), startLine, resultMatrix, startLine, middle - startLine);
    System.arraycopy(bottom.invoke(), middle, resultMatrix, middle, endLine - middle);
    return resultMatrix;
  }
}
