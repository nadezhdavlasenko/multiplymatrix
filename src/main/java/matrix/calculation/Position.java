package matrix.calculation;

import java.io.Serializable;

class Position implements Serializable {

  private static final long serialVersionUID = 7413534302008406560L;

  int i;
  int j;

  Position(int i, int j) {
    this.i = i;
    this.j = j;
  }
}
