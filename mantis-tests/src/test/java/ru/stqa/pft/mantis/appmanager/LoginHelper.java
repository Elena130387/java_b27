package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import java.util.List;

public class LoginHelper extends HelperBase{

  public  LoginHelper(ApplicationManager app){
    super(app);
  }

  public void login() {
    wd.get(app.getProperty("web.baseUrl") + "login.php");
    type(By.name("username"), app.getProperty("web.adminLogin"));
    click(By.cssSelector("input[type='submit']"));
    type(By.name("password"), app.getProperty("web.adminPassword"));
    click(By.cssSelector("input[type='submit']"));
  }

  public void resetPassword(String page, String id){
    wd.get(String.format("%s%s.php%s",app.getProperty("web.baseUrl"),page, id));
    click(By.cssSelector("input[value = 'Сбросить пароль']"));
  }

  public void newPassword(String newURL, String user, String newPass) {
    wd.get(newURL);
    type(By.name("realname"), user);
    type(By.name("password"), newPass);
    type(By.name("password_confirm"), newPass);
    click(By.cssSelector("span.submit-button button"));
  }

  public String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    //в потоке к элементам применяем фильтр - предикат.в результате получим только письма с нужным нам адресатом
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    //ищем ссылку с помощью регулярных выражений, но в нормальном варианте

    return regex.getText(mailMessage.text);//возвращает кусок текста построенный по регулятному выражению
  }
}
