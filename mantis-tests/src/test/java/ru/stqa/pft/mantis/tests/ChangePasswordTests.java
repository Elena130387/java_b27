package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.testng.Assert.assertTrue;

public class ChangePasswordTests extends TestBase{
  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void changePasswordTest() throws IOException, MessagingException {
    app.registration().userAuth("administrator", "root");
    UserData user = anyUser(app.dbHelper().allUsers());
    String newPassword = "newPassword";
    app.registration().resetUserPassword(user.getUsername());
    List<MailMessage> mailMessages = app.mail().waitForMail(1, 80000);
    String confimationLink = app.registration().findConfimLink(mailMessages, user.getEmail());
    app.registration().finish(confimationLink, user.getUsername(), newPassword);
    assertTrue(app.newSession().login(user.getUsername(), newPassword));

  }

  private UserData anyUser(List<UserData> allUsers) {
    List<UserData> copy = new ArrayList<>(allUsers);
    for (UserData user : copy){
      if(user.getUsername().equals("administrator")) {
        allUsers.remove(user);
      }
    }
    return allUsers.get((int)Math.random() * allUsers.size());
  }


  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }
}
