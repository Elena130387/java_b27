package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class GroupDeletionTests extends TestBase {
  @BeforeMethod
  public void ensurePreconditions(){
    app.goTO().groupPage();
    if (app.group().list().size() == 0) {
      app.group().create(new GroupData().withName("test_new"));
    }
  }

  @Test
  public void testGroupDeletion() throws Exception {
    List<GroupData> befor = app.group().list();
    int index = befor.size() - 2;
    app.group().delete(index);
    List<GroupData> after = app.group().list();
    Assert.assertEquals(after.size(), befor.size() - 1);

    befor.remove(index);
    Assert.assertEquals(befor, after);
  }
}
