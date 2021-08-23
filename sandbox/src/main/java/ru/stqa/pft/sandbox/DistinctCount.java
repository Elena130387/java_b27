package ru.stqa.pft.sandbox;

public class DistinctCount {
  public static void main(String[] args) {
    Point p1 = new Point(-10, 5, 10,7);
    Point p2 = new Point(-8, 3, 10,5);
    System.out.println("Расстояние между точками " + p1.x1 + ";" + p1.y1 + " и " + p1.x2 + ";" + p1.y2 + " = " + p1.distance());
    System.out.println("Расстояние между точками " + p2.x1 + ";" + p2.y1 + " и " + p2.x2 + ";" + p2.y2 + " = " + p2.distance());
  }
}
