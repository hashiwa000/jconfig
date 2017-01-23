package testapp;

import lombok.Getter;

@Getter
public class App2 {
  private int intValue = 2;
  private int intValue2;

  private double doubleValue = 2.0;
  private double doubleValue2;

  private String stringValue = "default";
  private String stringValue2;

  public App2() {
    intValue2 = 2;
    doubleValue2 = 2.0;
    stringValue2 = "default";
  }

}
