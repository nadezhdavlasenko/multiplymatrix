package matrix.calculation;

import java.util.concurrent.RecursiveTask;

public class MatrixMultiplyDividerTask extends RecursiveTask<boolean[][]> {
  boolean[][] matrix1;
  boolean[][] matrix2;
  int startLine;
  int endLine;
  boolean[][] result;

  public MatrixMultiplyDividerTask(
      boolean[][] matrix1, boolean[][] matrix2, int startLine, int endLine) {
    this.matrix1 = matrix1;
    this.matrix2 = matrix2;
    this.startLine = startLine;
    this.endLine = endLine;
    boolean[][] result = new boolean[matrix1.length][matrix1.length];
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
      for (int j = 0; j < result.length; j++) {
        result[i][j] = dividedTasks[i][j].join();
      }
    }
    return result;
  }

  private boolean[][] divideForComputing() {
    int topStartLine = startLine;
    int topEndLine = startLine + (endLine - startLine) / 2 + 1;
    MatrixMultiplyDividerTask top =
        new MatrixMultiplyDividerTask(matrix1, matrix2, topStartLine, topEndLine);
    top.fork();
    int bottomStartLine = topEndLine;
    int bottomEndline = endLine;
    MatrixMultiplyDividerTask bottom =
        new MatrixMultiplyDividerTask(matrix1, matrix2, bottomStartLine, bottomEndline);
    bottom.fork();

    System.arraycopy(top.invoke(), topStartLine, result, topStartLine, topEndLine - topStartLine);
    System.arraycopy(
        bottom.invoke(), bottomStartLine, result, bottomStartLine, bottomEndline - bottomStartLine);
    return result;
  }
}
