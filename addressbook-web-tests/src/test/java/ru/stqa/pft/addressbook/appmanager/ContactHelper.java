package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.List;

public class ContactHelper extends HelperBase {

  public ContactHelper (WebDriver wd) {
    super(wd);
  }

  public void submitNewContact() {
    click(By.name("submit"));
  }

  public void editNewContactData(ContactData contactData, boolean creation) {
    type(By.name("firstname"),contactData.getFirstname());
    type(By.name("lastname"),contactData.getLastname());
    type(By.name("address"),contactData.getAddress());
    type(By.name("home"),contactData.getHomePhones());
    type(By.name("mobile"),contactData.getMobilePhones());
    type(By.name("work"),contactData.getWorkPhones());
    type(By.name("email"),contactData.getEmail());

    if (creation) {
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void delete() {
    click(By.xpath("//input[@value='Delete']"));
    switchToAlertAccept();
  }

  private void selectContactById(int id) {
    wd.findElement(By.cssSelector(String.format("input[value='%s']", id))).click();
  }

  public void initContactModification(int index) {
    wd.findElements(By.xpath("//img[@alt='Edit']")).get(index).click();
  }

  private void initContactModifyByID(int id) {
    wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']",id))).click();
  }

  public void submitContactModification() {
    click(By.name("update"));
  }

  public void returnToHomePage() {
    click(By.linkText("home page"));
  }

  public void goToHome() {
    if (isElementPresent(By.id("maintable"))) {
      return;
    }
    click(By.linkText("home"));
  }

  public void create(ContactData contact, boolean creation) {
    goToContactCreate();
    editNewContactData(contact, creation);
    submitNewContact();
    contactCache = null;
    returnToHomePage();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    delete();
    isCssSelectorShow();
    goToHome();
    contactCache = null;
  }

  public void modify(ContactData contact) {
    initContactModifyByID(contact.getId());
    editNewContactData(contact, false);
    submitContactModification();
    contactCache = null;
    returnToHomePage();
  }

  public ContactData infoFromEditForm(ContactData contact) {
   initContactModifyByID(contact.getId());
   String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
   String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
   String home = wd.findElement(By.name("home")).getAttribute("value");
   String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
   String work = wd.findElement(By.name("work")).getAttribute("value");

   return new ContactData().
           withId(contact.getId()).withFirstname(firstname).withLastname(lastname).
           withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work);
 }

  public boolean isThereAContact() {
    return isElementPresent(By.xpath("//table[@id='maintable']/tbody/tr[2]/td/input"));
  }

  public int count() {
    return wd.findElements(By.name("entry")).size();
  }

  public void goToContactCreate() {
    if (isElementPresent(By.tagName("h1"))
            && wd.findElement(By.tagName("h1")).getText().equals("Edit / add address book entry")
            && isElementPresent(By.name("submit"))) {
      return;
    }
    click(By.linkText("add new"));
  }

  public void isCssSelectorShow() {
    wd.findElement(By.cssSelector("div.msgbox"));
  }

  private Contacts contactCache = null;

  public Contacts all() {
    if (contactCache != null) {
      return new Contacts (contactCache);
    }
    contactCache = new Contacts();
    List<WebElement> elements = wd.findElements(By.name("entry"));
    for (WebElement element: elements){
      List<WebElement> cells = element.findElements(By.tagName("td"));
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      String lastname = cells.get(1).getText();
      String name = cells.get(2).getText();
      String address = cells.get(3).getText();
      String email = cells.get(4).getText();
      String[] phones = cells.get(5).getText().split("\n");
      contactCache.add(new ContactData().withId(id).withFirstname(name).withLastname(lastname).
              withHomePhone(phones[0]).withMobilePhone(phones[1]).withWorkPhone(phones[2]));
    }
    return new Contacts (contactCache);
  }
}
