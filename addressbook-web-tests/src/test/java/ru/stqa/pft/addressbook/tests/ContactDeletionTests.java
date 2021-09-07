package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() throws Exception {
    app.getNavigationHelper().goToHome();
    if (! app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("Sergei", "Shapoval", "Spb, Verbnaya st, h.4", "89554050801", "8888@rambler.ru", "test_new"), true);
    }
    app.getContactHelper().selectFirstContact();
    app.getContactHelper().deleteContact();
    app.logout();
  }
}
