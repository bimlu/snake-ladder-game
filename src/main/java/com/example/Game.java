package com.example;

import java.util.Scanner;

public class Game {
  public static final int BOARDSIZE = 10;

  private int tokenX; // row
  private int tokenY; // col

  private Cell[][] board;
  private Ladder[] ladders;
  private Snake[] snakes;
  private Dice dice;

  private Cell cell;

  public Game() {

    board = new Cell[BOARDSIZE][BOARDSIZE];
    initializeBoard();

    ladders = Ladder.generateLadders();
    addLaddersToBoard();

    snakes = Snake.generateSnakes();
    addSnakesToBoard();

    // add token to board
    tokenX = BOARDSIZE - 1;
    tokenY = 0;
    board[tokenX][tokenY] = Cell.Token;

    this.cell = Cell.Empty;

    dice = new Dice();
  }

  public void play() throws InterruptedException {
    welcome();

    while (true) {

      printBoard();

      Scanner in = new Scanner(System.in);
      System.out.println("Press Enter to roll the dice...");
      in.nextLine();
      dice.roll();
      if (dice.value % 6 == 0) {
        System.out.println("You got 6. Rolling again..." + String.valueOf(dice.value));
        dice.roll();
      }
      System.out.println("Dice value: " + String.valueOf(dice.value));
      Thread.sleep(2000);
      System.out.println("Moving token forward by " + String.valueOf(dice.value) + " ...");
      Thread.sleep(2000);

      for (int i = 0; i < dice.value; i++) {
        moveForward();
      }
      dice.clear();

      climbLadder();

      climbDownSnake();

      if (reachedEnd()) {
        break;
      }
    }

    congrats();
  }

  private void printBoard() {
    StringBuilder out = new StringBuilder();

    out.append("\033[H\033[2J"); // clears the console
    out.append("--------------------------------\n");

    for (int i = 0; i < board.length; i++) {
      out.append("|");

      for (int j = 0; j < board.length; j++) {

        Cell cell = board[i][j];

        if (cell == Cell.Snake) {
          out.append(" x ");
        } else if (cell == Cell.Ladder) {
          out.append(" + ");
        } else if (cell == Cell.Empty) {
          out.append(" . ");
        } else if (cell == Cell.Token) {
          out.append(" @ ");
        }
      }
      out.append("|");
      out.append("\n");
    }

    out.append("--------------------------------\n");
    out.append("\n");

    System.out.print(out.toString());
  }

  private void moveForward() {
    board[tokenX][tokenY] = this.cell;

    if (tokenX % 2 == 0) {
      if (tokenY == 0) {
        tokenX--;
      } else {
        tokenY--;
      }
    } else {
      if (tokenY == Game.BOARDSIZE - 1) {
        tokenX--;
      } else {
        tokenY++;
      }
    }

    this.cell = board[tokenX][tokenY];
    board[tokenX][tokenY] = Cell.Token;

    printBoard();

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void climbLadder() {
    for (Ladder ladder : ladders) {
      if (tokenX == ladder.bottomX && tokenY == ladder.bottomY) {
        board[tokenX][tokenY] = Cell.Empty;

        tokenX = ladder.topX;
        tokenY = ladder.topY;

        board[tokenX][tokenY] = Cell.Ladder;
        return;
      }
    }
  }

  private void climbDownSnake() {
    for (Snake snake : snakes) {
      if (tokenX == snake.headX && tokenY == snake.headY) {
        board[tokenX][tokenY] = Cell.Empty;

        tokenX = snake.tailX;
        tokenY = snake.tailY;

        board[tokenX][tokenY] = Cell.Snake;
        return;
      }
    }
  }

  private boolean reachedEnd() {
    return (tokenX == 0) && (tokenY == 0);
  }

  private void congrats() {
    System.out.println("*** Congrats! you won.");
  }

  public void welcome() {
    System.out.println("\n\n*** Welcome to Snake and Ladder Game ***\n\n");
    System.out.println("Press Enter to Play...");
    Scanner in = new Scanner(System.in);
    in.nextLine();
  }

  private void initializeBoard() {
    // initialize board with empty cells
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board.length; j++) {
        board[i][j] = Cell.Empty;
      }
    }
  }

  private void addSnakesToBoard() {
    for (Snake snake : snakes) {
      board[snake.headX][snake.headY] = Cell.Snake;
      board[snake.tailX][snake.tailY] = Cell.Snake;
    }
  }

  private void addLaddersToBoard() {
    for (Ladder ladder : ladders) {
      board[ladder.bottomX][ladder.bottomY] = Cell.Ladder;
      board[ladder.topX][ladder.topY] = Cell.Ladder;
    }
  }
}
