package jconfig;

import org.junit.Test;
import static org.junit.Assert.*;

import testapp.App2;

public class ConfigTest2 {
  App2 app = new App2();

  @Test
  public void testInt() {
    assertEquals(2, app.getIntValue());
    assertEquals(2, app.getIntValue2());
  }

  @Test
  public void testDouble() {
    assertEquals(2.0, app.getDoubleValue(), 0);
    assertEquals(2.0, app.getDoubleValue2(), 0);
  }

  @Test
  public void testFloat() {
    //assertEquals(1.0, app.getFloatValue(), 0);
  }

  @Test
  public void testString() {
    assertEquals("default", app.getStringValue());
    assertEquals("default", app.getStringValue2());
  }

  @Test
  public void testStaticInt() {
    //assertEquals(1, App.getStaticIntValue());
  }
}

