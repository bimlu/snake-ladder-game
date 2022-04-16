package com.example;

public class Dice {
  protected int value = 0;

  protected void roll() {

    value += (int) (Math.random() * 6 + 1);
  }

  protected void clear() {
    value = 0;
  }
}
