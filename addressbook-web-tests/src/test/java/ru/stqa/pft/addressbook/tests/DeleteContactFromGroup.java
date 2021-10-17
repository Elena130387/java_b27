package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Set;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class DeleteContactFromGroup extends TestBase {
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
    }else {
      groupNew = app.db().groups().iterator().next();
    }
    
    if (app.db().contacts().size() == 0){
      contactNew = new ContactData()
              .withFirstname("Elena").withLastname("Shapoval").withAddress("Spb, Verbnaya st, h.4").withHomePhone("14141")
              .withMobilePhone("89554050801").withWorkPhone("7898").withEmail("8888@rambler.ru").withEmail2("").withEmail3("");
      app.contact().goToHome();
      app.contact().create(contactNew, true);
      assertTrue(app.db().contacts().contains(contactNew.withId
              (app.db().contacts().stream().mapToInt((c) -> c.getId()).max().getAsInt())));
      app.contact().addToGroup(contactNew, groupNew);
      Set<GroupData> contactGroupsAfter = app.db().contactById(contactNew.getId()).getGroups();
      assertTrue(contactGroupsAfter.contains(groupNew));
    }
  }

  @Test
  public void testDeleteContactFromGroup() {
   ContactData modifiedCont = app.db().contacts().iterator().next();
   Groups groups = app.db().groups();
   GroupData groupDelFrom = new GroupData();
   app.contact().goToHome();
   if (modifiedCont.getGroups().size() == 0) {
     groupDelFrom = groups.iterator().next();
     app.contact().addToGroup(modifiedCont, groupDelFrom);
     Set<GroupData> contactGroupsAfter = app.db().contactById(modifiedCont.getId()).getGroups();
     assertTrue(contactGroupsAfter.contains(groupDelFrom));
    } else {
     groupDelFrom = modifiedCont.getGroups().iterator().next();
   }
    app.contact().selectedContactsInGroup(groupDelFrom);
    app.contact().deleteFromGroup(modifiedCont);
    Set<GroupData> contactGroupsAfter = app.db().contactById(modifiedCont.getId()).getGroups();
    assertFalse(contactGroupsAfter.contains(groupDelFrom));
  }
}
