package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.*;

public class GroupDeletionTests extends TestBase {
  @BeforeMethod
  public void ensurePreconditions(){
    app.goTO().groupPage();
    if (app.group().all().size() == 0) {
      app.group().create(new GroupData().withName("test_new"));
    }
  }

  @Test
  public void testGroupDeletion() throws Exception {
    Groups befor = app.group().all();
    GroupData deletedGroup = befor.iterator().next();
    app.group().delete(deletedGroup);
    assertThat(app.group().count(), equalTo(befor.size() - 1));
    Groups after = app.group().all();
    assertThat(after, equalTo(befor.withOut(deletedGroup)));
  }
}
