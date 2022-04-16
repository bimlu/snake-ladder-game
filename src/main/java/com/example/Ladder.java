package com.example;

public class Ladder {

  public int bottomX;
  public int bottomY;
  public int topX;
  public int topY;

  public Ladder(int bottomX, int bottomY, int topX, int topY) {
    this.bottomX = bottomX;
    this.bottomY = bottomY;
    this.topX = topX;
    this.topY = topY;
  }

  public static Ladder[] generateLadders() {
    Ladder[] ladders = new Ladder[2];

    ladders[0] = new Ladder(2, 2, 4, 5);
    ladders[1] = new Ladder(4, 1, 6, 6);

    return ladders;
  }
}
