package ru.stqa.pft.sandbox;

public class Point {
  public double x1;
  public double y1;

  public Point (double x1, double y1) {
    this.x1 = x1;
    this.y1 = y1;

  }
  public double distance(Point extPoint) {
    return Math.sqrt((extPoint.x1 - this.x1) * (extPoint.x1 - this.x1) - (extPoint.y1 - this.y1) * (extPoint.y1 - this.y1));
  }
}
