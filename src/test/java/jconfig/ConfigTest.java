package jconfig;

import org.junit.Test;
import static org.junit.Assert.*;

import testapp.App;

public class ConfigTest {
  @Test
  public void test() {
    //assertEquals(3, Sample.add(1, 2));
    App app = new App();
    assertEquals(app.getValue(), 1);
  }
}

