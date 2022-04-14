package com.example;

public class Game {
  public static final int BOARDSIZE = 10;

  private int[][] board;
  private Ladder[] ladders;
  private Food[] foods;
  private Snake snake;
  private Dice dice;

  public Game() {
    board = new int[BOARDSIZE][BOARDSIZE];
    ladders = generateLadders();
    foods = generateFoods();
    snake = new Snake();
    dice = new Dice();
  }

  private Food[] generateFoods() {
    Food[] foods = new Food[4];

    foods[0] = new Food(2, 3);
    foods[1] = new Food(4, 5);
    foods[2] = new Food(3, 10);
    foods[3] = new Food(6, 1);

    return foods;
  }

  private Ladder[] generateLadders() {
    Ladder[] ladders = new Ladder[2];

    ladders[0] = new Ladder(2, 2, 4, 4);
    ladders[1] = new Ladder(4, 1, 6, 6);

    return ladders;
  }

  public void play() {
    welcome();

    while (true) {
      dice.clear();
      dice.roll();

      if (dice.value == 6) {
        dice.roll();
      }

      if (canMoveForward()) {
        snake.moveForward(dice.value);
      }

      if (canClimbLadder()) {
        snake.climbLadder(1, 2);
      }

      if (reachedEnd()) {
        break;
      }

    }

    congrats();
  }

  private void congrats() {
  }

  private boolean reachedEnd() {
    return false;
  }

  private boolean canClimbLadder() {
    return true;
  }

  private boolean canMoveForward() {
    return true;
  }

  public void welcome() {
    System.out.println("*** Welcome to Snake and Ladder Game ***\n");
  }
}
