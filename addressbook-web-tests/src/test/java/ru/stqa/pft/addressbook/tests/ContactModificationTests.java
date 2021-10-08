package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.*;
import static org.testng.Assert.assertEquals;

public class ContactModificationTests extends TestBase {
  @BeforeMethod
  public void ensurePreconditions(){
    if (app.db().contacts().size() == 0){
      app.contact().goToHome();
      app.contact().create(new ContactData()
              .withFirstname("Elena").withLastname("Shapoval").withAddress("Spb, Verbnaya st, h.4").withHomePhone("14141")
              .withMobilePhone("89554050801").withWorkPhone("7898").withEmail("8888@rambler.ru").withGroup("test_new"), true);
    }
  }
  @Test
  public void testContactModification() {
    Contacts befor = app.db().contacts();
    app.contact().goToHome();
    ContactData modifiedCont = befor.iterator().next();
    ContactData contact = new ContactData()
            .withId(modifiedCont.getId()).withFirstname("Alex").withLastname("Shapoval").withAddress("Spb, Verbnaya st, h.4").withHomePhone("14141")
            .withMobilePhone("89554050801").withWorkPhone("7898").withEmail("8888@rambler.ru").withEmail3("777@rambler.ru").withGroup("test_new");
    app.contact().modify(contact);
    assertEquals(app.contact().count(), befor.size());
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(befor.withOut(modifiedCont).withAdded(contact)));
   // app.logout();
  }
}
