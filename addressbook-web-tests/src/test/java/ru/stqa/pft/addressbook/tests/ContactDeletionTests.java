package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() throws Exception {
    app.getNavigationHelper().goToHome();
    int befor = app.getContactHelper().getContactCount();
    if (! app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("Sergei", "Shapoval", "Spb, Verbnaya st, h.4", "89554050801", "8888@rambler.ru", "test_new"), true);
    }
    app.getContactHelper().selectFirstContact();
    app.getContactHelper().deleteContact();
    app.getContactHelper().isCssSelectorShow();
    app.getNavigationHelper().goToHome();
    int after = app.getContactHelper().getContactCount();
    Assert.assertEquals(after, befor - 1);
    app.logout();
  }
}
