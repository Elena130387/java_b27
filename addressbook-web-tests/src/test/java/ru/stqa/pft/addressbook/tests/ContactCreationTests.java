package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class ContactCreationTests extends TestBase {
  @Test
  public void testContactCreation() throws Exception {
    app.getNavigationHelper().goToHome();
    List<ContactData> befor = app.getContactHelper().getContactList();
    ContactData contact = new ContactData("Galy", "Shapoval", "Spb, Verbnaya st, h.4", "89554050801", "8888@rambler.ru", "test1");
    app.getContactHelper().createContact(contact, true);
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), befor.size() + 1);


    //Comparator<? super ContactData> byID = (Comparator<ContactData>) (o1, o2) -> Integer.compare(o1.getId(), o2.getId());

    //int max = after.stream().max((Comparator<ContactData>) (o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId();
    contact.setId(after.stream().max((Comparator<ContactData>) (o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
    befor.add(contact);
    Assert.assertEquals( new HashSet<Object>(after), new HashSet<Object>(befor));
    app.logout();
  }
}
