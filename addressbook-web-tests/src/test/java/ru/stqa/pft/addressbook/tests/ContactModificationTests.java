package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class ContactModificationTests extends TestBase {
  @BeforeMethod
  public void ensurePreconditions(){
    app.goTO().home();
    if (app.contact().all().size() == 0) {
      app.contact().create(new ContactData()
              .withFirstname("Galy").withLastname("Shapoval").withAddress("Spb, Verbnaya st, h.4").withMobile("89554050801").withEmail("8888@rambler.ru").withGroup("test1"), true);
    }
  }
  @Test
  public void testContactModification() {
    Set<ContactData> befor = app.contact().all();
    ContactData modifiedCont = befor.iterator().next();
    ContactData contact = new ContactData()
            .withId(modifiedCont.getId()).withFirstname("Genya").withLastname("Shapoval").withAddress("Spb, Verbnaya st, h.4").withMobile("89554050801").withEmail("8888@rambler.ru");
    app.contact().modify(contact);
    Set<ContactData> after = app.contact().all();
    Assert.assertEquals(after.size(), befor.size());

    befor.remove(modifiedCont);
    befor.add(contact);
    Assert.assertEquals(after, befor);
   // app.logout();
  }


}
