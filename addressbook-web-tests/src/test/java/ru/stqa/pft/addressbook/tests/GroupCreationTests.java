package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.List;

public class GroupCreationTests extends TestBase{

  @Test
  public void testGroupCreation() throws Exception {
    app.getNavigationHelper().gotoGroupPage();
    List<GroupData> befor = app.getGroupHelper().getGroupList();
    GroupData group = new GroupData("test_new", null, null);
    app.getGroupHelper().createGroup(group);
    List<GroupData> after = app.getGroupHelper().getGroupList();
    Assert.assertEquals(after.size(), befor.size() + 1);

    int max = 0;
    for (GroupData g: after){
      if (g.getId() > max) {
        max = g.getId();
      }
    }
    group.setId(max);
    befor.add(group);
    Assert.assertEquals( new HashSet<Object>(after), new HashSet<Object>(befor));

    app.logout();
  }
}
