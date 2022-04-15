package com.example;

public class Ladder {

  public int startRowCoord;
  public int startColCoord;
  public int endRowCoord;
  public int endColCoord;

  public Ladder(int startX, int startY, int endX, int endY) {
    this.startRowCoord = startX;
    this.startColCoord = startY;
    this.endRowCoord = endX;
    this.endColCoord = endY;
  }
}
