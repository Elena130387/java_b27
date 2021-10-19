package ru.stqa.pft.mantis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "mantis_user_table")
public class UserData {
  @Id
  int id ;

  @Column(name = "username")
  String username;

  @Column(name = "email")
  String email;

  @Column(name = "password")
  String password;

  @Override
  public String toString() {
    return "Users{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            '}';
  }

  public int getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public void withId(int id) {
    this.id = id;
  }

  public UserData WithUsername(String username) {
    this.username = username;
    return this;
  }

  public UserData WithEmail(String email) {
    this.email = email;
    return this;
  }

  public UserData WithPassword(String password) {
    this.password = password;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserData user = (UserData) o;
    return id == user.id &&
            Objects.equals(username, user.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username);
  }
}
