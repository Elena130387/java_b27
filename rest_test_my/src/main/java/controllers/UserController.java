package controllers;

import com.google.gson.Gson;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import pojo.DataUser;
import pojo.UserResponse;

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
}
