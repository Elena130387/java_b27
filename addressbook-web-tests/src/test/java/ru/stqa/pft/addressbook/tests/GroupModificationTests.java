package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.*;
import static org.testng.Assert.assertEquals;

public class GroupModificationTests extends TestBase {
  @BeforeMethod
  public void ensurePreconditions(){
    app.goTO().groupPage();
    if (app.group().all().size() == 0) {
      app.group().create(new GroupData().withName("test_new"));
    }
  }

  @Test
  public void testGroupModification() {
    Groups befor = app.group().all();
    GroupData modifiedGroup = befor.iterator().next();
    GroupData group = new GroupData()
            .withId(modifiedGroup.getId()).withName("test_new5").withHeader("test_new2").withFooter("test_new2");
    app.group().modify(group);
    assertThat(app.group().count(), equalTo(befor.size()));
    Groups after = app.group().all();
    assertThat(after, equalTo(befor.withOut(modifiedGroup).withAdded(group)));
   // app.logout();
  }
}
