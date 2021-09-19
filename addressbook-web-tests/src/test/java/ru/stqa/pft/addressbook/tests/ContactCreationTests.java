package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {
  @Test
  public void testContactCreation() throws Exception {
    app.goTO().home();
    List<ContactData> befor = app.contact().list();
    ContactData contact = new ContactData("Galy", "Shapoval", "Spb, Verbnaya st, h.4", "89554050801", "8888@rambler.ru", "test1");
    app.contact().create(contact, true);
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), befor.size() + 1);

    befor.add(contact);
    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    befor.sort(byId);
    after.sort(byId);
    Assert.assertEquals( after, befor);
   // app.logout();
  }
}
