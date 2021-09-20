package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.GroupData;
import java.util.Set;

public class GroupCreationTests extends TestBase{

  @Test
  public void testGroupCreation() throws Exception {
    app.goTO().groupPage();
    Set<GroupData> befor = app.group().all();
    GroupData group = new GroupData().withName("test2");
    app.group().create(group);
    Set<GroupData> after = app.group().all();
    Assert.assertEquals(after.size(), befor.size() + 1);
    group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
    befor.add(group);
    Assert.assertEquals(befor, after);

   // app.logout();
  }
}
