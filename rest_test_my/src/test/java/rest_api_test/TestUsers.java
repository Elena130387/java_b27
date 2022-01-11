package rest_api_test;

import controllers.UserController;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojo.DataUser;
import pojo.UserResponse;
import pojo.UserResponseArray;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class TestUsers {
int i;
  @DataProvider
  public Iterator<Object[]> validUsersFromCSV() throws IOException {
    List<Object[]> list = new ArrayList<Object[]>();
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/userCreateTestData.csv")));
    String line = reader.readLine();
    while (line != null) {
      String[] split = line.split(";");
      list.add(new Object[]{new DataUser().withName(split[0]).withEmail(split[1])
              .withGender(split[2]).withStatus(split[3])});
      line = reader.readLine();
    }
    return list.iterator();
  }

  @Test(dataProvider = "validUsersFromCSV")
  public void testUser(DataUser dataUser) throws IOException {
    ++i;
    UserController userController = new UserController();
    UserResponse userResponse = userController.sendRequestPost(dataUser);
    assertEquals(userResponse.getData(), dataUser);
    UserResponse userResponseData = userController.getUserByID(userResponse.getData().getId());
    assertEquals(userResponseData.getData(),userResponse.getData());
    DataUser dataUserNew = new DataUser().withId(userResponse.getData().getId()).withName(userResponse.getData().getName()).withEmail("svetlana_" + i + "@gmail.ru")
                    .withGender("female").withStatus(userResponse.getData().getStatus());
   // UserResponseArray userResponseArray = userController.sendRequestGet();
    UserResponse userResponseNew = userController.changeUser(dataUserNew);
    assertEquals(userResponseNew.getData(),dataUserNew);
    userController.saveAsCsv(userResponse.getData().getId());
  }
}

