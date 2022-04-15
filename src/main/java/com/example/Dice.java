package com.example;

import java.util.Scanner;

public class Dice {
  protected int value;

  protected void roll() {
    Scanner in = new Scanner(System.in);
    System.out.println("Press Enter to roll the dice: ");
    in.nextLine();
    in.close();

    value = (int) (Math.random() * 6 + 1);
  }

  protected void clear() {
    value = 0;
  }
}
