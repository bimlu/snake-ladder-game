package com.example;

public class Dice {
  protected int value = 0;

  protected void roll() {

    value += (int) (Math.random() * 6 + 1);
    // For testing
    // Scanner in = new Scanner(System.in);
    // System.out.print("Enter dummy dice value: ");
    // value = in.nextInt();
  }

  protected void clear() {
    value = 0;
  }
}
