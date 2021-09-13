package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {
  @Test
  public void testContactCreation() throws Exception {
    app.getNavigationHelper().goToHome();
    int befor = app.getContactHelper().getContactCount();
    app.getContactHelper().createContact(new ContactData("ALEX", "Shapoval", "Spb, Verbnaya st, h.1", "89554050801", "8888@rambler.ru", "test_new"), true);
    int after = app.getContactHelper().getContactCount();
    Assert.assertEquals(after, befor + 1);
    app.logout();
  }
}
