package ru.stqa.pft.mantis.model;

public class Status {
  private int id;
  private String status;

  public int getId() {
    return id;
  }

  public Status withId(int id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return status;
  }

  public Status withStatus(String status) {
    this.status = status;
    return this;
  }
}
