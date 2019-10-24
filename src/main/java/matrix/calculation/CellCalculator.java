package matrix.calculation;

class CellCalculator {

  private CellCalculator() {}

  static boolean calculateCell(boolean[][] matrix1, boolean[][] matrix2, Position position) {
    boolean sum = false;
    for (int k = 0; k < matrix1.length; k++) {
      sum = sum ^ (matrix1[position.i][k] && matrix2[k][position.j]);
    }
    return sum;
  }
}
