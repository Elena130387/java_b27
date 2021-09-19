package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

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
    List<GroupData> befor = app.group().list();
    int index = befor.size() - 2;
    GroupData group = new GroupData()
            .withId(befor.get(index).getId()).withName("test_new5").withHeader("test_new2").withFooter("test_new2");
    app.group().modify(index, group);
    List<GroupData> after = app.group().list();
    Assert.assertEquals(after.size(), befor.size());

    befor.remove(index);
    befor.add(group);
    Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    befor.sort(byId);
    after.sort(byId);
    Assert.assertEquals(befor, after);
   // app.logout();
  }
}
