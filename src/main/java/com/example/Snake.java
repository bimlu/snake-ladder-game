package com.example;

public class Snake {
  protected int[] valuesX;
  protected int[] valuesY;

  public Snake() {
    this.valuesX = new int[] { 2, 1, 0 };
    this.valuesY = new int[] { 0, 0, 0 };
  }

  void moveForward(int diceValue) {
    for (int i = 1; i <= diceValue; i++) {

      for (int k = 0; k < valuesX.length; k++) {

        int[] nextCell = getNextCell(valuesX[k], valuesY[k]);

        valuesX[k] = nextCell[0];
        valuesY[k] = nextCell[1];
      }
    }
  }

  void climbLadder(int ladderEndX, int ladderEndY) {
    valuesX[0] = ladderEndX;
    valuesY[0] = ladderEndY;

    for (int k = 1; k < valuesX.length; k++) {
      int[] newCell = getPreviousCell(valuesX[k - 1], valuesY[k - 1]);

      valuesX[k] = newCell[0];
      valuesY[k] = newCell[1];
    }
  }

  private int[] getNextCell(int x, int y) {

    if (y % 2 == 0) {
      if (x == Game.BOARDSIZE - 1) {
        y++;
      } else {
        x++;
      }
    } else {
      if (x == 0) {
        y++;
      } else {
        x--;
      }
    }

    return new int[] { x, y };
  }

  private int[] getPreviousCell(int x, int y) {

    if (y % 2 == 0) {
      if (x == 0) {
        y--;
      } else {
        x--;
      }
    } else {
      if (x == Game.BOARDSIZE - 1) {
        y--;
      } else {
        x++;
      }
    }

    return new int[] { x, y };
  }

}
