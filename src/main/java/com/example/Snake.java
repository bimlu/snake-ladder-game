package com.example;

public class Snake {
  protected int[] rowCoords;
  protected int[] colCoords;

  public Snake() {
    this.rowCoords = new int[] { Game.BOARDSIZE - 1, Game.BOARDSIZE - 1, Game.BOARDSIZE - 1 };
    this.colCoords = new int[] { 2, 1, 0 };
  }

  void moveForward(int diceValue) {
    for (int i = 1; i <= diceValue; i++) {

      for (int k = 0; k < rowCoords.length; k++) {

        int[] nextCell = getNextCell(rowCoords[k], colCoords[k]);

        rowCoords[k] = nextCell[0];
        colCoords[k] = nextCell[1];
      }
    }
  }

  void climbLadder(int ladderEndX, int ladderEndY) {
    rowCoords[0] = ladderEndX;
    colCoords[0] = ladderEndY;

    for (int k = 1; k < rowCoords.length; k++) {
      int[] newCell = getPreviousCell(rowCoords[k - 1], colCoords[k - 1]);

      rowCoords[k] = newCell[0];
      colCoords[k] = newCell[1];
    }
  }

  private int[] getNextCell(int row, int col) {

    if (row % 2 != 0) {
      if (col == Game.BOARDSIZE - 1) {
        row--;
      } else {
        col++;
      }
    } else {
      if (col == 0) {
        row--;
      } else {
        col--;
      }
    }

    return new int[] { row, col };
  }

  private int[] getPreviousCell(int row, int col) {

    if (row % 2 != 0) {
      if (col == 0) {
        row++;
      } else {
        col--;
      }
    } else {
      if (col == Game.BOARDSIZE - 1) {
        row++;
      } else {
        col++;
      }
    }

    return new int[] { row, col };
  }

  public int getHeadX() {
    return rowCoords[0];
  }

  public int getHeadY() {
    return colCoords[0];
  }

}
