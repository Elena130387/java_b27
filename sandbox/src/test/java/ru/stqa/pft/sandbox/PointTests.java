package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {
  @Test
  public void testDistinct(){
    Point p1 = new Point(2,3);
    Point p2 = new Point(-4,3);
    Point p3 = new Point(7,2);
    Assert.assertEquals(p1.distance(p2), 6.0);
    Assert.assertEquals(p2.distance(p3), 10.954451150103322);
    Assert.assertEquals(p3.distance(p1), 4.898979485566356);
  }
}
