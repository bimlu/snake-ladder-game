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
    this.dice = new Dice();
  }

  public void play() throws InterruptedException {
    welcome();

    while (true) {
      printBoard();

      System.out.println("1. Roll the dice.\n");
      System.out.println("2. Show the ladders.\n");
      System.out.println("3. Show the snakes\n");

      Scanner in = new Scanner(System.in);
      System.out.print("\nPlease select an option: ");
      int choice = in.nextInt();

      switch (choice) {
        case 1:
          rollTheDice();
          break;
        case 2:
          showTheLadders();
          continue;
        case 3:
          showTheSnakes();
          continue;
        default:
          continue;
      }

      if (tokenX == 0 && (dice.value > tokenY)) {
        continue;
      }

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

  private void rollTheDice() throws InterruptedException {
    dice.roll();
    System.out.print("\n>>> ");
    System.out.print("Rolling dice...");
    clearCurrentLineAndWait(2000, 15);

    if (dice.value % 6 == 0) {
      dice.roll();
      System.out.print("You got 6. Rolling again...");
      clearCurrentLineAndWait(2000, 27);
    }

    System.out.print("Got " + String.valueOf(dice.value));
    clearCurrentLineAndWait(2000, 5);

    System.out.print("Moving token forward by " + String.valueOf(dice.value) + " ...");
    Thread.sleep(2000);
  }

  private void clearCurrentLineAndWait(int milliSec, int charCount) throws InterruptedException {
    Thread.sleep(milliSec);
    for (int i = 0; i < charCount; i++) {
      System.out.print("\b \b");
    }
  }

  private void showTheLadders() throws InterruptedException {
    for (Ladder ladder : ladders) {
      blinkAnimation(ladder.bottomX, ladder.bottomY, ladder.topX, ladder.topY, Cell.LadderBottom, Cell.LadderTop);
    }
  }

  private void showTheSnakes() throws InterruptedException {
    for (Snake snake : snakes) {
      blinkAnimation(snake.headX, snake.headY, snake.tailX, snake.tailY, Cell.SnakeHead, Cell.SnakeTail);
    }
  }

  private void printBoard() {
    StringBuilder out = new StringBuilder();

    out.append("\033[H\033[2J"); // clears the console
    out.append("     +--------------------+     \n");
    out.append("     |  SNAKE AND LADDER  |     \n");
    out.append("     +--------------------+     \n");
    out.append("\n");
    out.append("+------------------------------+\n");

    for (int i = 0; i < board.length; i++) {
      out.append("|");

      for (int j = 0; j < board.length; j++) {

        Cell cell = board[i][j];

        if (cell == Cell.Empty) {
          out.append(" . ");
        } else if (cell == Cell.Token) {
          out.append(" @ ");
        } else if (cell == Cell.LadderBottom) {
          out.append(" A ");
        } else if (cell == Cell.LadderTop) {
          out.append(" B ");
        } else if (cell == Cell.SnakeHead) {
          out.append(" X ");
        } else if (cell == Cell.SnakeTail) {
          out.append(" Y ");
        } else if (cell == Cell.Blink) {
          out.append(" * ");
        }
      }
      out.append("|");
      out.append("\n");
    }

    out.append("+------------------------------+\n");
    out.append("\n");

    System.out.print(out.toString());
  }

  private void moveForward() throws InterruptedException {
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
    Thread.sleep(500);

  }

  private void climbLadder() throws InterruptedException {
    for (Ladder ladder : ladders) {
      if (tokenX == ladder.bottomX && tokenY == ladder.bottomY) {
        tokenX = ladder.topX;
        tokenY = ladder.topY;

        blinkAnimation(ladder.bottomX, ladder.bottomY, ladder.topX, ladder.topY, Cell.LadderBottom, Cell.LadderTop);

        board[ladder.bottomX][ladder.bottomY] = Cell.LadderBottom;
        board[ladder.topX][ladder.topY] = Cell.Token;
        this.cell = Cell.LadderTop;

        return;
      }
    }
  }

  private void climbDownSnake() throws InterruptedException {
    for (Snake snake : snakes) {
      if (tokenX == snake.headX && tokenY == snake.headY) {
        tokenX = snake.tailX;
        tokenY = snake.tailY;

        blinkAnimation(snake.headX, snake.headY, snake.tailX, snake.tailY, Cell.SnakeHead, Cell.SnakeTail);

        board[snake.headX][snake.headY] = Cell.SnakeHead;
        board[snake.tailX][snake.tailY] = Cell.Token;
        this.cell = Cell.SnakeTail;

        return;
      }
    }
  }

  private void blinkAnimation(int ax, int ay, int bx, int by, Cell c1, Cell c2) throws InterruptedException {
    int timer = 6;

    while (timer > 0) {
      board[ax][ay] = Cell.Blink;
      board[bx][by] = Cell.Blink;
      printBoard();
      Thread.sleep(200);

      board[ax][ay] = c1;
      board[bx][by] = c2;
      printBoard();
      Thread.sleep(200);

      timer--;
    }
  }

  private void initializeBoard() {
    // initialize board with empty cells
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board.length; j++) {
        board[i][j] = Cell.Empty;
      }
    }
  }

  private void addLaddersToBoard() {
    for (Ladder ladder : ladders) {
      board[ladder.bottomX][ladder.bottomY] = Cell.LadderBottom;
      board[ladder.topX][ladder.topY] = Cell.LadderTop;
    }
  }

  private void addSnakesToBoard() {
    for (Snake snake : snakes) {
      board[snake.headX][snake.headY] = Cell.SnakeHead;
      board[snake.tailX][snake.tailY] = Cell.SnakeTail;
    }
  }

  private boolean reachedEnd() {
    return (tokenX == 0) && (tokenY == 0);
  }

  private void congrats() {
    System.out.println("*** Congrats! you won.");
  }

  public void welcome() {
    System.out.println("\n\n*** Welcome to snake and ladder game ***\n\n");
    System.out.println("Press enter to play...");
    Scanner in = new Scanner(System.in);
    in.nextLine();
  }
}
