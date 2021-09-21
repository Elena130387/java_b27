package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class ContactCreationTests extends TestBase {
  @Test
  public void testContactCreation() throws Exception {
    app.goTO().home();
    Set<ContactData> befor = app.contact().all();
    ContactData contact = new ContactData()
            .withFirstname("Galy").withLastname("Shapoval").withAddress("Spb, Verbnaya st, h.4").withMobile("89554050801").withEmail("8888@rambler.ru").withGroup("test_new");
    app.contact().create(contact, true);
    Set<ContactData> after = app.contact().all();
    Assert.assertEquals(after.size(), befor.size() + 1);
    contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt());

    befor.add(contact);
    Assert.assertEquals( after, befor);
   // app.logout();
  }
}
