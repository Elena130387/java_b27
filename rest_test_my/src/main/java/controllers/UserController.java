package controllers;

import com.google.gson.Gson;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import pojo.DataUser;
import pojo.UserResponse;
import pojo.UserResponseArray;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class UserController extends DataForController {
  private static final String path = "/users";
  public UserResponse sendRequestPost(DataUser dataUser) {
    String json = RestAssured.given()
            .accept("application/json")
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + (String.format(properties.getProperty("token"))))
            .body(dataUser)
            .post(String.format(properties.getProperty("url") + "%s", path)).asString();

    Gson gson = new Gson();
    return gson.fromJson(json, UserResponse.class);
  }

  public void saveAsCsv(Integer id) throws IOException {

    //   try (Writer writer = new FileWriter(properties.getProperty("fileSaveId"))) {
    //       writer.write(String.format("%s\n", id));

    File file = new File(properties.getProperty("fileSaveId"));
    FileWriter fr = null;
    try {
      fr = new FileWriter(file, true);
      fr.write(String.format("%s\n", id));

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        fr.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  public UserResponse getUserByID(int id){
    String json = RestAssured.given()
            .accept("application/json")
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + (String.format(properties.getProperty("token"))))
            .get (String.format(properties.getProperty("url") + "%s/%s", path,id)).asString();

    Gson gson = new Gson();
    return gson.fromJson(json, UserResponse.class);
  }
  public UserResponse changeUser(DataUser dataUser){
    String json = RestAssured.given()
            .accept("application/json")
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + (String.format(properties.getProperty("token"))))
            .body(dataUser)
            .patch(String.format(properties.getProperty("url") + "%s/%s", path, dataUser.getId())).asString();

    Gson gson = new Gson();
    return gson.fromJson(json, UserResponse.class);
  }

  public UserResponseArray sendRequestGet() {
    String json = RestAssured.given()
            .accept("application/json")
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + (String.format(properties.getProperty("token"))))
            .get (String.format(properties.getProperty("url"))).asString();
    Gson gson = new Gson();
    return gson.fromJson(json, UserResponseArray.class);
  }
}
