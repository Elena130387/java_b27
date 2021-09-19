package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase {
  @BeforeMethod
  public void ensurePreconditions(){
    app.goTO().home();
    if (app.contact().list().size() == 0) {
      app.contact().create(new ContactData()
              .withFirstname("Sergei").withLastname("Shapoval").withAddress("Spb, Verbnaya st, h.4").withMobile("89554050801").withEmail("8888@rambler.ru").withGroup("test_new"), true);
    }
  }

  @Test
  public void testContactDeletion() throws Exception {
    List<ContactData> befor = app.contact().list();
    int index = befor.size() - 2;
    app.contact().delete(index);
    app.goTO().home();
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), befor.size() - 1);
    
    befor.remove(index);
    Assert.assertEquals(befor, after);
   // app.logout();
  }
}
