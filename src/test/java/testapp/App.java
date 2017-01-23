package testapp;

import lombok.Getter;

@Getter
public class App {
  private int intMaxValue;
  private int intMinValue;

  private long longMaxValue;
  private long longMinValue;

  private float floatMaxValue;
  private float floatMaxValue2;
  private float floatMinValue;
  private float floatMinValue2;
  private float floatMinNormal;
  private float floatMinNormal2;

  private double doubleMaxValue;
  private double doubleMaxValue2;
  private double doubleMinValue;
  private double doubleMinValue2;
  private double doubleMinNormal;
  private double doubleMinNormal2;

  private char charValue;
  private char charMaxValue;
  private char charMinValue;

  private short shortMaxValue;
  private short shortMinValue;

  private byte byteValue;
  private byte byteMaxValue;
  private byte byteMinValue;

  private String stringValue;
  private String stringEmpty;

  private static int staticIntValue;

  public static void main(String[] args) {
    //System.out.println("intValue is " + new App().getIntValue());
    System.out.println("staticIntValue is " + staticIntValue);
  }

  public static int getStaticIntValue() {
    return staticIntValue;
  }
}
