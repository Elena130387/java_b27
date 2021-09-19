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
      app.contact().create(new ContactData("Sergei", "Shapoval", "Spb, Verbnaya st, h.4", "89554050801", "8888@rambler.ru", "test_new"), true);
    }
  }
  @Test
  public void testContactModification() {
    List<ContactData> befor = app.contact().list();
    int index = befor.size() - 2;
    ContactData contact = new ContactData(befor.get(index).getId(),"Olga", "Shapoval", "Spb, Verbnaya st, h.4", "89554050801", "8888@rambler.ru", null);
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
