package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase {
  @BeforeMethod
  public void ensurePreconditions(){
    app.getNavigationHelper().goToHome();
    if (! app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("Sergei", "Shapoval", "Spb, Verbnaya st, h.4", "89554050801", "8888@rambler.ru", "test_new"), true);
    }
  }

  @Test
  public void testContactDeletion() throws Exception {
    List<ContactData> befor = app.getContactHelper().getContactList();
    int index = befor.size() - 2;
    app.getContactHelper().deleteContact(index);
    app.getNavigationHelper().goToHome();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), befor.size() - 1);
    
    befor.remove(index);
    Assert.assertEquals(befor, after);

   // app.logout();
  }
}
