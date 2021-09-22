package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.*;
import static org.testng.Assert.assertEquals;

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
    Contacts befor = app.contact().all();
    ContactData deletedCont = befor.iterator().next();
    app.contact().delete(deletedCont);
    app.goTO().home();
    assertEquals(app.contact().count(), befor.size() - 1);
    Contacts after = app.contact().all();
    assertThat(after, equalTo(befor.withOut(deletedCont)));
   // app.logout();
  }
}
