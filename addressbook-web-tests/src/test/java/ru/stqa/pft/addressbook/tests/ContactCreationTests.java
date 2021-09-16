package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.List;

public class ContactCreationTests extends TestBase {
  @Test
  public void testContactCreation() throws Exception {
    app.getNavigationHelper().goToHome();
    List<ContactData> befor = app.getContactHelper().getContactList();
    ContactData contact = new ContactData("TEST3", "Shapoval", "Spb, Verbnaya st, h.4", "89554050801", "8888@rambler.ru", null);
    app.getContactHelper().createContact(contact, true);
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), befor.size() + 1);

    int max = 0;
    for (ContactData с: after){
      if (с.getId() > max) {
        max = с.getId();
      }
    }
    //contact.setId(max);
    befor.add(contact);
    Assert.assertEquals( new HashSet<Object>(after), new HashSet<Object>(befor));
    app.logout();
  }
}
