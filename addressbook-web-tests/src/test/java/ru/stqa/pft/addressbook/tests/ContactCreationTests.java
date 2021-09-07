package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {
  @Test
  public void testContactCreation() throws Exception {
    app.getContactHelper().createContact(new ContactData("ALEX", "Shapoval", "Spb, Verbnaya st, h.1", "89554050801", "8888@rambler.ru", "test_new"), true);
    app.logout();
  }
}
