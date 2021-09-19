package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
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
    type(By.name("mobile"),contactData.getMobile());
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

  public void selectContact(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
   // click(By.xpath("//table[@id='maintable']/tbody/tr[2]/td/input"));
  }

  public void initContactModification(int index) {
    wd.findElements(By.xpath("//img[@alt='Edit']")).get(index).click();
   // click(By.xpath("//img[@alt='Edit']"));
  }

  public void submitContactModification() {
    click(By.name("update"));
  }

  public void returnToHomePage() {
    click(By.linkText("home page"));
  }

  public void create(ContactData contact, boolean creation) {
    goToContactCreate();
    editNewContactData(contact, creation);
    submitNewContact();
    returnToHomePage();
  }
  public void modify(int index, ContactData contact) {
    initContactModification(index);
    editNewContactData(contact, false);
    submitContactModification();
    returnToHomePage();
  }
  public void delete(int index) {
    selectContact(index);
    delete();
    isCssSelectorShow();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.xpath("//table[@id='maintable']/tbody/tr[2]/td/input"));
  }

  public void goToContactCreate() {
    if (isElementPresent(By.tagName("h1"))
            && wd.findElement(By.tagName("h1")).getText().equals("Edit / add address book entry")
            && isElementPresent(By.name("submit"))) {
      return;
    }
    click(By.linkText("add new"));
  }

  public int getContactCount() {
    return wd.findElements(By.name("entry")).size();
  }

  public void isCssSelectorShow() {
    wd.findElement(By.cssSelector("div.msgbox"));
  }

  public List<ContactData> list() {
    List<ContactData> contacts = new ArrayList<ContactData>();
    List<WebElement> elements = wd.findElements(By.name("entry"));
    for (WebElement element: elements){
      List<WebElement> cells = element.findElements(By.tagName("td"));
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      String lastname = cells.get(1).getText();
      String name = cells.get(2).getText();
      String address = cells.get(3).getText();
      String email = cells.get(4).getText();
      String mobile = cells.get(5).getText();
      ContactData contact = new ContactData(id, name, lastname, null, null, null, null);
      contacts.add(contact);
    }
    return contacts;
  }
}
