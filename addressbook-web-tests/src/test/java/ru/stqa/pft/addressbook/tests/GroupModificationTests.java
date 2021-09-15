package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.List;

public class GroupModificationTests extends TestBase {
  @Test
  public void testGroupModification() {
    app.getNavigationHelper().gotoGroupPage();
    if (! app.getGroupHelper().isThereAGroup()) {
      app.getGroupHelper().createGroup(new GroupData("test_new", null, null));
    }
    List<GroupData> befor = app.getGroupHelper().getGroupList();
    app.getGroupHelper().selectGroup(befor.size() - 2);
    app.getGroupHelper().initGroupModification();
    GroupData group = new GroupData( befor.get(befor.size() - 2).getId(), "test_new5", "test_new2", "test_new2");
    app.getGroupHelper().fillGroupForm(group);
    app.getGroupHelper().submitGroupModification();
    app.getGroupHelper().returnToGroupPage();
    List<GroupData> after = app.getGroupHelper().getGroupList();
    Assert.assertEquals(after.size(), befor.size());

    befor.remove(befor.size() - 2);
    befor.add(group);
    Assert.assertEquals( new HashSet<Object>(after), new HashSet<Object>(befor));
    app.logout();
  }
}
