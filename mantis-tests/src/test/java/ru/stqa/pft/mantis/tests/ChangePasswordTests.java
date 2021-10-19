package ru.stqa.pft.mantis.tests;

import org.hibernate.service.spi.ServiceException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.appmanager.DbHelper;
import ru.stqa.pft.mantis.appmanager.HttpSession;
import ru.stqa.pft.mantis.appmanager.LoginHelper;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class ChangePasswordTests extends TestBase{
  LoginHelper loginHelper = null;
  UserData userData = null;

  @BeforeMethod
  public void start(){
    loginHelper = new LoginHelper(app);
    loginHelper.login();
    app.mail().start();
    userData = new DbHelper().userData();
    assertNotNull(userData);
  }

  @Test
  public void testChangePass() throws IOException, ServiceException {
    String newPass = "password_new";
    loginHelper.resetPassword("manage_user_edit_page", String.format("?user_id=%s",userData.getId()));
    List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);//ждем 10 секунд
    String confirmationLink = loginHelper.findConfirmationLink(mailMessages, userData.getEmail());
    loginHelper.newPassword(confirmationLink,userData.getUsername(), newPass);
    HttpSession session = app.newSession();
    assertTrue(session.login(userData.getUsername(), newPass));
    assertTrue(session.isLoggedInAs(userData.getUsername()));
  }

  @AfterMethod(alwaysRun = true)
  public void stop(){
    app.mail().stop();
  }
}