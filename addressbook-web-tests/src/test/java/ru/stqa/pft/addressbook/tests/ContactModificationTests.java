package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.*;
import static org.testng.Assert.assertEquals;

public class ContactModificationTests extends TestBase {
  @BeforeMethod
  public void ensurePreconditions(){
    if (app.db().contacts().size() == 0){
      if (app.db().groups().size() == 0) {
        app.goTO().groupPage();
        app.group().create(new GroupData().withName("test_new").withFooter("test_new").withHeader("test_new"));
      }
      app.contact().goToHome();
      app.contact().create(new ContactData()
              .withFirstname("Elena").withLastname("Shapoval").withAddress("Spb, Verbnaya st, h.4").withHomePhone("14141")
              .withMobilePhone("89554050801").withWorkPhone("7898").withEmail("8888@rambler.ru").withEmail2("").withEmail3("")
              .inGroup(app.db().groups().iterator().next()), true);
    }
  }
  @Test
  public void testContactModification() {
    Contacts befor = app.db().contacts();
    Groups groups = app.db().groups();
    app.contact().goToHome();
    ContactData modifiedCont = befor.iterator().next();
    ContactData contact = new ContactData()
            .withId(modifiedCont.getId()).withFirstname("Alex").withLastname("Shapoval").withAddress("Spb, Verbnaya st, h.4").withHomePhone("14141")
            .withMobilePhone("89554050801").withWorkPhone("7898").withEmail("8888@rambler.ru").withEmail2("").inGroup(groups.iterator().next())
            .withEmail3("777@rambler.ru");
    app.contact().modify(contact);
    assertEquals(app.contact().count(), befor.size());
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(befor.withOut(modifiedCont).withAdded(contact)));
    verifyContactListInUI();
  }
}
