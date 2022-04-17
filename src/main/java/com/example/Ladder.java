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
    // from wikipedia
    int[] ladderBottoms = new int[] { 12, 51, 57, 76, 78 };
    int[] ladderTops = new int[] { 20, 78, 84, 92, 94 };

    Ladder[] ladders = new Ladder[ladderBottoms.length];

    for (int i = 0; i < ladders.length; i++) {
      ladders[i] = createLadder(ladderBottoms[i], ladderTops[i]);
    }

    return ladders;
  }

  private static Ladder createLadder(int ladderBottom, int ladderTop) {
    int n = Game.BOARDSIZE * Game.BOARDSIZE;
    // by observation
    int bottomX = (n - ladderBottom) / Game.BOARDSIZE;
    int bottomY = (n - ladderBottom) % Game.BOARDSIZE;
    int topX = (n - ladderTop) / Game.BOARDSIZE;
    int topY = (n - ladderTop) % Game.BOARDSIZE;

    return new Ladder(bottomX, bottomY, topX, topY);
  }
}
