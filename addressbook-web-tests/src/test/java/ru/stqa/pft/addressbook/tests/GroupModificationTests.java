package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class GroupModificationTests extends TestBase {
  @BeforeMethod
  public void ensurePreconditions(){
    app.goTO().groupPage();
    if (app.group().list().size() == 0) {
      app.group().create(new GroupData().withName("test_new"));
    }
  }

  @Test
  public void testGroupModification() {
    Set<GroupData> befor = app.group().all();
    GroupData modifiedGroup = befor.iterator().next();
    GroupData group = new GroupData()
            .withId(modifiedGroup.getId()).withName("test_new5").withHeader("test_new2").withFooter("test_new2");
    app.group().modify(group);
    Set<GroupData> after = app.group().all();
    Assert.assertEquals(after.size(), befor.size());

    befor.remove(modifiedGroup);
    befor.add(group);
    Assert.assertEquals(befor, after);
   // app.logout();
  }
}
