package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {
  @Test
  public void testDistinct(){
    Point p1 = new Point(-5,3,8,14);
    Assert.assertEquals(p1.distance(), 6.928203230275509);
    Point p2 = new Point(-4,3,10,14);
    Assert.assertEquals(p2.distance(), 8.660254037844387);
    Point p3 = new Point(2,-8,-9,-10);
    Assert.assertEquals(p3.distance(), 10.816653826391969);
  }
}
