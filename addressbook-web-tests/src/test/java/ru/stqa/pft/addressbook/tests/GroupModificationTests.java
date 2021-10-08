package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.*;

public class GroupModificationTests extends TestBase {
  @BeforeMethod
  public void ensurePreconditions(){
    if (app.db().groups().size() == 0){
      app.goTO().groupPage();
      app.group().create(new GroupData().withName("test_new"));
    }
  }

  @Test
  public void testGroupModification() {
    Groups befor = app.db().groups();
    GroupData modifiedGroup = befor.iterator().next();
    GroupData group = new GroupData()
            .withId(modifiedGroup.getId()).withName("test_new5").withHeader("test_new2").withFooter("test_new2");
    app.goTO().groupPage();
    app.group().modify(group);
    assertThat(app.group().count(), equalTo(befor.size()));
    Groups after = app.db().groups();
    assertThat(after, equalTo(befor.withOut(modifiedGroup).withAdded(group)));
   // app.logout();
  }
}
