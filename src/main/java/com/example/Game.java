package com.example;

public class Game {
  public static final int BOARDSIZE = 10;

  private Cell[][] board;
  private Ladder[] ladders;
  private Food[] foods;
  private Snake snake;
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
      board[ladder.startX][ladder.startY] = Cell.Ladder;
      board[ladder.endX][ladder.endY] = Cell.Ladder;
    }

    foods = generateFoods();
    // add foods to board
    for (Food food : foods) {
      board[food.foodX][food.foodY] = Cell.Food;
    }

    snake = new Snake();
    // add snake to board
    for (int i = 0; i < snake.valuesX.length; i++) {
      board[snake.valuesX[i]][snake.valuesY[i]] = Cell.Snake;
    }

    dice = new Dice();
  }

  private Food[] generateFoods() {
    Food[] foods = new Food[4];

    foods[0] = new Food(2, 3);
    foods[1] = new Food(4, 5);
    foods[2] = new Food(3, 9);
    foods[3] = new Food(6, 1);

    return foods;
  }

  private Ladder[] generateLadders() {
    Ladder[] ladders = new Ladder[2];

    ladders[0] = new Ladder(2, 2, 4, 4);
    ladders[1] = new Ladder(4, 1, 6, 6);

    return ladders;
  }

  private void printBoard() {
    for (Cell[] row : board) {
      for (Cell cell : row) {

        if (cell == Cell.Snake) {
          System.out.print("#");
        } else if (cell == Cell.Ladder) {
          System.out.print("^");
        } else if (cell == Cell.Empty) {
          System.out.print(".");
        } else if (cell == Cell.Food) {
          System.out.print("*");
        }
      }
      System.out.println();
    }
  }

  public void play() {
    welcome();

    while (true) {
      dice.clear();

      printBoard();

      dice.roll();

      if (dice.value == 6) {
        dice.roll();
      }

      if (canMoveForward()) {

        for (int i = 0; i < snake.valuesX.length; i++) {
          board[snake.valuesX[i]][snake.valuesY[i]] = Cell.Empty;
        }

        snake.moveForward(dice.value);

        for (int i = 0; i < snake.valuesX.length; i++) {
          board[snake.valuesX[i]][snake.valuesY[i]] = Cell.Snake;
        }
      }

      climbLadder();

      if (reachedEnd()) {
        break;
      }

    }

    congrats();
  }

  private void congrats() {
  }

  private boolean reachedEnd() {
    return (snake.getHeadX() == Game.BOARDSIZE - 1) && (snake.getHeadY() == Game.BOARDSIZE - 1);
  }

  private void climbLadder() {
    for (Ladder ladder : ladders) {
      if (snake.getHeadX() == ladder.startX && snake.getHeadY() == ladder.startY) {

        for (int i = 0; i < snake.valuesX.length; i++) {
          board[snake.valuesX[i]][snake.valuesY[i]] = Cell.Empty;
        }

        snake.climbLadder(ladder.endX, ladder.endY);

        for (int i = 0; i < snake.valuesX.length; i++) {
          board[snake.valuesX[i]][snake.valuesY[i]] = Cell.Snake;
        }

        return;
      }
    }
  }

  private boolean canMoveForward() {
    return true;
  }

  public void welcome() {
    System.out.println("*** Welcome to Snake and Ladder Game ***\n");
  }
}
