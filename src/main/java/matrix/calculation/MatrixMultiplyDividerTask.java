package matrix.calculation;

import java.util.concurrent.RecursiveTask;

public class MatrixMultiplyDividerTask extends RecursiveTask<boolean[][]> {
  boolean[][] matrix1;
  boolean[][] matrix2;
  Position position;
  int startLine;
  int endLine;

  public MatrixMultiplyDividerTask(
      boolean[][] matrix1, boolean[][] matrix2, int startLine, int endLine) {
    this.matrix1 = matrix1;
    this.matrix2 = matrix2;
    this.startLine = startLine;
    this.endLine = endLine;
  }

  @Override
  protected boolean[][] compute() {
    //    ForkJoinPool commonPool = ForkJoinPool.commonPool();

    boolean[][] result = new boolean[matrix1.length][matrix1.length];
    MatrixLineColumnMultiplyTask[][] dividedTasks =
        new MatrixLineColumnMultiplyTask[matrix1.length][matrix1[0].length];
    if (endLine - startLine < 4) {
      for (int i = startLine; i < endLine; i++) {
        for (int j = 0; j < matrix2.length; j++) {
          dividedTasks[i][j] =
              new MatrixLineColumnMultiplyTask(matrix1, matrix2, new Position(i, j));
          dividedTasks[i][j].fork();
        }
      }
      for (int i = startLine; i < endLine; i++) {

        for (int j = 0; j < result.length; j++) {
          result[i][j] = dividedTasks[i][j].join();
        }
      }

    } else {
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
          bottom.invoke(),
          bottomStartLine,
          result,
          bottomStartLine,
          bottomEndline - bottomStartLine);
      return result;
    }
    return result;
  }
}
