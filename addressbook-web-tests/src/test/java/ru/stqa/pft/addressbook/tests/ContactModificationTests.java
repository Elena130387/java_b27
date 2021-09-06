package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {
  @Test
  public void testContactModification() {
    app.getNavigationHelper().goToHome();
    app.getContactHelper().selectFirstContact();
    app.getContactHelper().initContactModification();
    app.getContactHelper().editNewContactData(new ContactData("Helena", "Shapoval", "Spb, Verbnaya st, h.4", "89554050801", "8888@rambler.ru", null), false);
    app.getContactHelper().submitContactModification();
    app.getContactHelper().returnToHomePage();
    app.logout();
  }
}
