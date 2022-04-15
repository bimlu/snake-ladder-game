package com.example;

import java.util.Scanner;

public class Dice {
  protected int value;

  protected void roll() {
    Scanner in = new Scanner(System.in);
    System.out.println("Press Enter to roll the dice: ");
    int userInput = in.nextInt();
    System.out.println(userInput);

    value = (int) (Math.random() * 6 + 1);
    // System.out.println("dice value: " + String.valueOf(value));
    value = userInput;
  }

  protected void clear() {
    value = 0;
  }
}
