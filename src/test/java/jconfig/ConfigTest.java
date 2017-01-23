package jconfig;

import org.junit.Test;
import static org.junit.Assert.*;

import testapp.App;

public class ConfigTest {
  App app = new App();

  @Test
  public void testInt() {
    assertEquals(Integer.MAX_VALUE, app.getIntMaxValue());
    assertEquals(Integer.MIN_VALUE, app.getIntMinValue());
  }

  @Test
  public void testLong() {
    assertEquals(Long.MAX_VALUE, app.getLongMaxValue());
    assertEquals(Long.MIN_VALUE, app.getLongMinValue());
  }

  @Test
  public void testFloat() {
    assertEquals(Float.MAX_VALUE, app.getFloatMaxValue(), 0);
    assertEquals(Float.MIN_VALUE, app.getFloatMinValue(), 0);
    assertEquals(Float.MIN_NORMAL, app.getFloatMinNormal(), 0);

    assertEquals(Float.MAX_VALUE, app.getFloatMaxValue2(), 0);
    assertEquals(Float.MIN_VALUE, app.getFloatMinValue2(), 0);
    assertEquals(Float.MIN_NORMAL, app.getFloatMinNormal2(), 0);
  }

  @Test
  public void testDouble() {
    assertEquals(Double.MAX_VALUE, app.getDoubleMaxValue(), 0);
    assertEquals(Double.MIN_VALUE, app.getDoubleMinValue(), 0);
    assertEquals(Double.MIN_NORMAL, app.getDoubleMinNormal(), 0);

    assertEquals(Double.MAX_VALUE, app.getDoubleMaxValue2(), 0);
    assertEquals(Double.MIN_VALUE, app.getDoubleMinValue2(), 0);
    assertEquals(Double.MIN_NORMAL, app.getDoubleMinNormal2(), 0);
  }

  @Test
  public void testChar() {
    assertEquals('a', app.getCharValue());
    assertEquals(Character.MAX_VALUE, app.getCharMaxValue());
    assertEquals(Character.MIN_VALUE, app.getCharMinValue());
  }

  @Test
  public void testShort() {
    assertEquals(Short.MAX_VALUE, app.getShortMaxValue());
    assertEquals(Short.MIN_VALUE, app.getShortMinValue());
  }

  @Test
  public void testByte() {
    assertEquals(0x01, app.getByteValue());
    assertEquals(Byte.MAX_VALUE, app.getByteMaxValue());
    assertEquals(Byte.MIN_VALUE, app.getByteMinValue());
  }

  @Test
  public void testString() {
    assertEquals("any", app.getStringValue());
    assertEquals("", app.getStringEmpty());
  }

  @Test
  public void testStaticInt() {
    assertEquals(1, App.getStaticIntValue());
  }
}

