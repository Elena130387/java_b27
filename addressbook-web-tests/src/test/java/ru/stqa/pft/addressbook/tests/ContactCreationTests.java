package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class ContactCreationTests extends TestBase {
  @Test
  public void testContactCreation() throws Exception {
    app.getNavigationHelper().goToHome();
    List<ContactData> befor = app.getContactHelper().getContactList();
    app.getContactHelper().createContact(new ContactData("ALEX", "Shapoval", "Spb, Verbnaya st, h.1", "89554050801", "8888@rambler.ru", "test_new"), true);
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), befor.size() + 1);
    app.logout();
  }
}
