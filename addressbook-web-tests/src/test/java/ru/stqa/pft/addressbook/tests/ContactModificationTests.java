package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {
  @Test
  public void testContactModification() {
    app.getNavigationHelper().goToHome();
    if (! app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("Sergei", "Shapoval", "Spb, Verbnaya st, h.4", "89554050801", "8888@rambler.ru", "test_new"), true);
    }
    List<ContactData> befor = app.getContactHelper().getContactList();
   // app.getContactHelper().selectContact(befor.size() - 2);
    app.getContactHelper().initContactModification(befor.size() - 2);
    ContactData contact = new ContactData(befor.get(befor.size() - 2).getId(),"Olga", "Shapoval", "Spb, Verbnaya st, h.4", "89554050801", "8888@rambler.ru", null);
    app.getContactHelper().editNewContactData(contact, false);
    app.getContactHelper().submitContactModification();
    app.getContactHelper().returnToHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), befor.size());

    befor.remove(befor.size() - 2);
    befor.add(contact);
    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    befor.sort(byId);
    after.sort(byId);
    Assert.assertEquals(after, befor);
    app.logout();
  }
}
