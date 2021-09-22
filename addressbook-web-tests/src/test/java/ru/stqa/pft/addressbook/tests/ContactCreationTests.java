package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;


public class ContactCreationTests extends TestBase {
  @Test
  public void testContactCreation() throws Exception {
    app.goTO().home();
    Contacts befor = app.contact().all();
    ContactData contact = new ContactData()
            .withFirstname("Elena").withLastname("Shapoval").withAddress("Spb, Verbnaya st, h.4").withMobile("89554050801").withEmail("8888@rambler.ru").withGroup("test_new");
    app.contact().create(contact, true);
    Contacts after = app.contact().all();
    assertThat(after.size(), equalTo(befor.size() + 1));

    assertThat(after, equalTo(befor.withAdded(
            contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
   // app.logout();
  }
}
