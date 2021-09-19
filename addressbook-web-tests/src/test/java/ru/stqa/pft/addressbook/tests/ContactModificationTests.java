package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {
  @BeforeMethod
  public void ensurePreconditions(){
    app.goTO().home();
    if (app.contact().list().size() == 0) {
      app.contact().create(new ContactData()
              .withFirstname("Galy").withLastname("Shapoval").withAddress("Spb, Verbnaya st, h.4").withMobile("89554050801").withEmail("8888@rambler.ru").withGroup("test1"), true);
    }
  }
  @Test
  public void testContactModification() {
    List<ContactData> befor = app.contact().list();
    int index = befor.size() - 2;
    ContactData contact = new ContactData()
            .withId(befor.get(index).getId()).withFirstname("Olga").withLastname("Shapoval").withAddress("Spb, Verbnaya st, h.4").withMobile("89554050801").withEmail("8888@rambler.ru");
    app.contact().modify(index, contact);
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), befor.size());

    befor.remove(index);
    befor.add(contact);
    Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
    befor.sort(byId);
    after.sort(byId);
    Assert.assertEquals(after, befor);
   // app.logout();
  }


}
