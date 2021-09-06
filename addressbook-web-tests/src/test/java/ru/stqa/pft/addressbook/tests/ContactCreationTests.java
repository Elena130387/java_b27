package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {
  @Test
  public void testContactCreation() throws Exception {
    app.getNavigationHelper().goToContactCreat();
    app.getContactHelper().editNewContactData(new ContactData("Helena", "Shapoval", "Spb, Verbnaya st, h.1", "89554050801", "8888@rambler.ru", "test1"), true);
    app.getContactHelper().submitNewContact();
    app.getContactHelper().returnToHomePage();
    app.logout();
  }

}
