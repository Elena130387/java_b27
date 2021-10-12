package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Set;

import static org.testng.Assert.*;

public class AddContactToGroupTests extends TestBase {
  @BeforeMethod
  public void ensurePreconditions(){
    if (app.db().groups().size() == 0) {
      app.goTO().groupPage();
      app.group().create(new GroupData().withName("test_new").withFooter("test_new").withHeader("test_new"));
    }
    if (app.db().contacts().size() == 0){
      app.contact().goToHome();
      app.contact().create(new ContactData()
              .withFirstname("Elena").withLastname("Shapoval").withAddress("Spb, Verbnaya st, h.4").withHomePhone("14141")
              .withMobilePhone("89554050801").withWorkPhone("7898").withEmail("8888@rambler.ru").withEmail2("").withEmail3(""), true);
    }
  }

  @Test
  public void testAddContactToGroup() {
    Contacts befor = app.db().contacts();
    Groups groups = app.db().groups();
    GroupData groupAdd = new GroupData();
    ContactData modifiedCont = befor.iterator().next();
    Set<GroupData> contactGroupsBefor = modifiedCont.getGroups();
    for (GroupData group: groups) {
      if (!contactGroupsBefor.contains(group)) {
        groupAdd =  group;
        break;
      }
    }
    app.contact().goToHome();
    app.contact().addToGroup(modifiedCont, groupAdd);
    assertEquals(app.contact().count(), befor.size());
    Set<GroupData> contactGroupsAfter = app.db().contactById(modifiedCont.getId()).getGroups();
    assertTrue(contactGroupsAfter.contains(groupAdd));
  }
}
