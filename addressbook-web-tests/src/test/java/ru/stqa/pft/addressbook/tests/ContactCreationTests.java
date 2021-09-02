package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {
  @Test
  public void testContactCreation() throws Exception {
    app.goToContactCreat();
    app.editNewContactData(new ContactData("Helena", "Shapoval", "Spb, Verbnaya st, h.1", "89554050801", "8888@rambler.ru"));
    app.submitNewContact();
  }

}
