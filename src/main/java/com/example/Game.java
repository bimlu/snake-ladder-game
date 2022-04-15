package com.example;

public class Game {
  public static final int BOARDSIZE = 10;

  private int tokenX; // row
  private int tokenY; // col

  private Cell[][] board;
  private Ladder[] ladders;
  private Snake[] snakes;
  private Dice dice;

  public Game() {

    board = new Cell[BOARDSIZE][BOARDSIZE];
    // initialize board with empty cells
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board.length; j++) {
        board[i][j] = Cell.Empty;
      }
    }

    ladders = generateLadders();
    // add ladders to board
    for (Ladder ladder : ladders) {
      board[ladder.bottomX][ladder.bottomY] = Cell.Ladder;
      board[ladder.topX][ladder.topY] = Cell.Ladder;
    }

    snakes = generateSnakes();
    // add snakes to board
    for (Snake snake : snakes) {
      board[snake.headX][snake.headY] = Cell.Snake;
      board[snake.tailX][snake.tailY] = Cell.Snake;
    }

    // add token to board
    tokenX = BOARDSIZE - 1;
    tokenY = 0;
    board[tokenX][tokenY] = Cell.Token;

    dice = new Dice();
  }

  private Snake[] generateSnakes() {
    Snake[] snakes = new Snake[2];

    snakes[0] = new Snake(6, 2, 4, 4);
    snakes[1] = new Snake(6, 1, 3, 6);

    return snakes;
  }

  private Ladder[] generateLadders() {
    Ladder[] ladders = new Ladder[2];

    ladders[0] = new Ladder(2, 2, 4, 5);
    ladders[1] = new Ladder(4, 1, 6, 6);

    return ladders;
  }

  private void printBoard() {
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board.length; j++) {

        Cell cell = board[i][j];

        if (cell == Cell.Snake) {
          System.out.print(" x ");
        } else if (cell == Cell.Ladder) {
          System.out.print(" + ");
        } else if (cell == Cell.Empty) {
          System.out.print(" . ");
        } else if (cell == Cell.Token) {
          System.out.print(" @ ");
        }
      }
      System.out.println();
    }
    System.out.println();
  }

  public void play() {
    welcome();

    while (true) {
      System.out.print("\033[H\033[2J");
      System.out.flush();

      dice.clear();

      printBoard();

      dice.roll();

      if (dice.value == 6) {
        dice.roll();
      }

      for (int i = 0; i < dice.value; i++) {
        board[tokenX][tokenY] = Cell.Empty;
        moveForward();
        board[tokenX][tokenY] = Cell.Token;
      }

      climbLadder();

      climbDownSnake();

      if (reachedEnd()) {
        break;
      }
    }

    congrats();
  }

  private void moveForward() {
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
    System.out.println("*** Welcome to Snake and Ladder Game ***\n");
  }
}
