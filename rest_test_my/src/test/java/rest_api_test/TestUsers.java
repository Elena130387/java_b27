package rest_api_test;

import controllers.UserController;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojo.DataUser;
import pojo.UserResponse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class TestUsers {

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
  public void testUserCreate(DataUser dataUser) {
    UserController userController = new UserController();
    UserResponse userResponse;
    userResponse = userController.sendRequestPost(dataUser);
    assertEquals(userResponse.getData(), dataUser);
  }
}
