package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;
import java.util.Set;

public class ContactDeletionTests extends TestBase {
  @BeforeMethod
  public void ensurePreconditions(){
    app.goTO().home();
    if (app.contact().all().size() == 0) {
      app.contact().create(new ContactData()
              .withFirstname("Sergei").withLastname("Shapoval").withAddress("Spb, Verbnaya st, h.4").withMobile("89554050801").withEmail("8888@rambler.ru").withGroup("test_new"), true);
    }
  }

  @Test
  public void testContactDeletion() throws Exception {
    Set<ContactData> befor = app.contact().all();
    ContactData deletedCont = befor.iterator().next();
    app.contact().delete(deletedCont);
    app.goTO().home();
    Set<ContactData> after = app.contact().all();
    Assert.assertEquals(after.size(), befor.size() - 1);
    
    befor.remove(deletedCont);
    Assert.assertEquals(befor, after);
   // app.logout();
  }
}
