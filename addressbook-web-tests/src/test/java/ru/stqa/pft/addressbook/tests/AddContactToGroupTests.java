package ru.stqa.pft.addressbook.tests;

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
    GroupData groupNew = new GroupData();
    ContactData contactNew = new ContactData();
    if (app.db().groups().size() == 0) {
      groupNew = new GroupData().withName("test_new").withFooter("test_new").withHeader("test_new");
      app.goTO().groupPage();
      app.group().create(groupNew);
      assertTrue(app.db().groups().contains(groupNew.withId
              (app.db().groups().stream().mapToInt((g) -> g.getId()).max().getAsInt())));
    }
    if (app.db().contacts().size() == 0){
      contactNew = new ContactData()
              .withFirstname("Elena").withLastname("Shapoval").withAddress("Spb, Verbnaya st, h.4").withHomePhone("14141")
              .withMobilePhone("89554050801").withWorkPhone("7898").withEmail("8888@rambler.ru").withEmail2("").withEmail3("");
      app.contact().goToHome();
      app.contact().create(contactNew, true);
      assertTrue(app.db().contacts().contains(contactNew.withId
              (app.db().contacts().stream().mapToInt((c) -> c.getId()).max().getAsInt())));
    }
  }

  @Test
  public void testAddContactToGroup() {
    Contacts contacts = app.db().contacts();
    Groups groups = app.db().groups();
    GroupData groupAdd = new GroupData();
    ContactData modifiedCont = contacts.iterator().next();
    Set<GroupData> contactGroupsBefor = modifiedCont.getGroups();
    int n = 0;
    for (GroupData group: groups) {
      n++;
      if (!contactGroupsBefor.contains(group)) {
        groupAdd =  group;
        break;
      } else if(n == groups.size()){
        app.goTO().groupPage();
        n++;
        groupAdd = new GroupData().withName("test_new_" + n).withFooter("test_new_" + n)
                .withHeader("test_new_" + n);
        app.group().create(groupAdd);
        assertTrue(app.db().groups().contains(groupAdd));
      }
    }
    app.contact().goToHome();
    app.contact().addToGroup(modifiedCont, groupAdd);
    Set<GroupData> contactGroupsAfter = app.db().contactById(modifiedCont.getId()).getGroups();
    assertTrue(contactGroupsAfter.contains(groupAdd));
  }
}
