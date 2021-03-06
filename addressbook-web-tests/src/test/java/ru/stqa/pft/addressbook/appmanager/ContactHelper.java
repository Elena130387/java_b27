package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

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
    attach(By.name("photo"),contactData.getPhoto());
    type(By.name("address"),contactData.getAddress());
    type(By.name("home"),contactData.getHomePhone());
    type(By.name("mobile"),contactData.getMobilePhone());
    type(By.name("work"),contactData.getWorkPhone());
    type(By.name("email"),contactData.getEmail());
    type(By.name("email2"),contactData.getEmail2());
    type(By.name("email3"),contactData.getEmail3());

    if (creation) {
      if (contactData.getGroups().size() > 0){
        Assert.assertTrue(contactData.getGroups().size() == 1);
        new Select(wd.findElement(By.name("new_group")))
                .selectByVisibleText(contactData.getGroups().iterator().next().getName());
      }
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
   String address = wd.findElement(By.name("address")).getAttribute("value");
   String home = wd.findElement(By.name("home")).getAttribute("value");
   String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
   String work = wd.findElement(By.name("work")).getAttribute("value");
   String email = wd.findElement(By.name("email")).getAttribute("value");
   String email2 = wd.findElement(By.name("email2")).getAttribute("value");
   String email3 = wd.findElement(By.name("email3")).getAttribute("value");

   return new ContactData().
           withId(contact.getId()).withFirstname(firstname).withLastname(lastname).
           withAddress(address).withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work).
           withEmail(email).withEmail2(email2).withEmail3(email3);
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
      String allEmails = cells.get(4).getText();
      String allPhones = cells.get(5).getText();
      contactCache.add(new ContactData().withId(id).withFirstname(name).withLastname(lastname).
              withAddress(address).withAllEmails(allEmails).withAllPhones(allPhones));
    }
    return new Contacts (contactCache);
  }

  public void addToGroup(ContactData contact, GroupData group) {
    selectContactById(contact.getId());
    addToGroup(group);
    goToHome();
    contactCache = null;
  }

  public void addToGroup(GroupData group) {
    new Select(wd.findElement(By.name("to_group"))).selectByValue(String.valueOf(group.getId()));
    click(By.name("add"));
  }

  public void deleteFromGroup(ContactData contact) {
    selectContactById(contact.getId());
    deleteFromGroup();
    goToHome();
    selectAllContacts();
    contactCache = null;
  }

  private void selectAllContacts() {
    new Select(wd.findElement(By.name("group"))).selectByVisibleText("[all]");
  }

  private void deleteFromGroup() {
    click(By.name("remove"));
  }

  public void selectedContactsInGroup(GroupData group) {
    new Select(wd.findElement(By.name("group"))).selectByValue(String.valueOf(group.getId()));
  }
}
