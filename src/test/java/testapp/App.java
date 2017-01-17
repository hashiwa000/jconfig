package testapp;

import jconfig.Config;

public class App {
  private int value;
  private static int staticValue;

  public static void main(String[] args) {
    System.out.println("value is " + new App().getValue());
    System.out.println("staticValue is " + staticValue);
  }

  public int getValue() {
    return value;
  }
}
