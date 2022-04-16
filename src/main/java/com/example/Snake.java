package com.example;

public class Snake {
  public int headX;
  public int headY;
  public int tailX;
  public int tailY;

  public Snake(int headX, int headY, int tailX, int tailY) {
    this.headX = headX;
    this.headY = headY;
    this.tailX = tailX;
    this.tailY = tailY;
  }

  public static Snake[] generateSnakes() {
    // from wikipedia
    int[] snakeHeads = new int[] { 41, 44, 49, 52, 58, 62, 69, 73, 84, 92, 95, 99 };
    int[] snakeTails = new int[] { 9, 23, 14, 24, 40, 17, 32, 10, 45, 61, 27, 11 };

    Snake[] snakes = new Snake[snakeHeads.length];

    for (int i = 0; i < snakes.length; i++) {
      snakes[i] = createSnake(snakeHeads[i], snakeTails[i]);
    }

    return snakes;
  }

  private static Snake createSnake(int snakeHead, int snakeTail) {
    int n = Game.BOARDSIZE * Game.BOARDSIZE;
    // by observation
    int headX = (n - snakeHead) / 10;
    int headY = (n - snakeHead) % 10;
    int tailX = (n - snakeTail) / 10;
    int tailY = (n - snakeTail) % 10;

    return new Snake(headX, headY, tailX, tailY);
  }
}
