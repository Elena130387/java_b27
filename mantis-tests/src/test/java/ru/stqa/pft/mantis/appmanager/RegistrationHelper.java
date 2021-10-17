package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class RegistrationHelper extends HelperBase{

  public RegistrationHelper(ApplicationManager app) {
    super(app);
  }

  public void start(String username, String email) {
    wd.get(app.getProperty("web.baseUrl") + "/signup_page.php");
    type(By.name("username"), username);
    type(By.name("email"), email);
    //click(By.cssSelector("input[value = 'Signup']"));
    click(By.cssSelector("input[value = 'Зарегистрироваться']"));
  }

  public void finish(String confirmationLink, String user, String password) {
    wd.get(confirmationLink);
    type(By.name("realname"), user);
    type(By.name("password"), password);
    type(By.name("password_confirm"), password);
    //click(By.cssSelector("input[value='submit']"));
    click(By.cssSelector("span.submit-button button"));
    //click(By.cssSelector("input[value='Изменить пользователя']"));
  }
}
