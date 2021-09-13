package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupCreationTests extends TestBase{

  @Test
  public void testGroupCreation() throws Exception {
    app.getNavigationHelper().gotoGroupPage();
    int befor = app.getGroupHelper().getGroupCount();
    app.getGroupHelper().createGroup(new GroupData("test_new", null, null));
    int after = app.getGroupHelper().getGroupCount();
    Assert.assertEquals(after, befor + 1);
    app.logout();
  }

}
