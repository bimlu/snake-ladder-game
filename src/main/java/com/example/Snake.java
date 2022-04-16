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
    Snake[] snakes = new Snake[2];

    snakes[0] = new Snake(6, 2, 4, 4);
    snakes[1] = new Snake(6, 1, 3, 6);

    return snakes;
  }
}
