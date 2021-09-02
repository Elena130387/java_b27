package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() throws Exception {
    app.getNavigationHelper().goToHome();
    app.getContactHelper().selectFirstContact();
    app.getContactHelper().deleteContact();
    app.logout();
  }
}
