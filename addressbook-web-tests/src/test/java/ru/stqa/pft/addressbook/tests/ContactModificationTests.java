package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactModificationTests extends TestBase {
  @Test
  public void testContactModification() {
    app.getNavigationHelper().goToHome();
    if (! app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("Sergei", "Shapoval", "Spb, Verbnaya st, h.4", "89554050801", "8888@rambler.ru", "test_new"), true);
    }
    app.getContactHelper().selectFirstContact();
    app.getContactHelper().initContactModification();
    app.getContactHelper().editNewContactData(new ContactData("Elena", "Shapoval", "Spb, Verbnaya st, h.4", "89554050801", "8888@rambler.ru", null), false);
    app.getContactHelper().submitContactModification();
    app.getContactHelper().returnToHomePage();
    app.logout();
  }
}
