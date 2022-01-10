package pojo;

import java.util.Objects;

public class DataUser {
  private Integer id;
  private String name;
  private String email;
  private String gender;
  private String status;

  public Integer getId() {
    return id;
  }

  public DataUser withId(Integer id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public DataUser withName(String name) {
    this.name = name;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public DataUser withEmail(String email) {
    this.email = email;
    return this;
  }

  public String getGender() {
    return gender;
  }

  public DataUser withGender(String gender) {
    this.gender = gender;
    return this;
  }

  public String getStatus() {
    return status;
  }

  public DataUser withStatus(String status) {
    this.status = status;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DataUser dataUser = (DataUser) o;
    return Objects.equals(name, dataUser.name) && Objects.equals(email, dataUser.email) && Objects.equals(gender, dataUser.gender) && Objects.equals(status, dataUser.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, email, gender, status);
  }

  @Override
  public String toString() {
    return "DataUser{" +
            "name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", gender='" + gender + '\'' +
            ", status='" + status + '\'' +
            '}';
  }
}



