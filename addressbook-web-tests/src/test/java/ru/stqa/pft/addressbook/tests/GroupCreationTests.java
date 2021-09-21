package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.GroupData;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase{

  @Test
  public void testGroupCreation() throws Exception {
    app.goTO().groupPage();
    Set<GroupData> befor = app.group().all();
    GroupData group = new GroupData().withName("test2");
    app.group().create(group);
    Set<GroupData> after = app.group().all();
    assertThat(after.size(), equalTo(befor.size() + 1));
    group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
    befor.add(group);
    assertThat(after, equalTo(befor));

   // app.logout();
  }
}
