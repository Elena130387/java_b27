package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase{

  @DataProvider
  public Iterator<Object[]> validGroups(){
    List<Object[]> list = new ArrayList<Object[]>();
    list.add(new Object[]{new GroupData().withName("test1").withHeader("header 1").withFooter("footer 1")});
    list.add(new Object[]{new GroupData().withName("test2").withHeader("header 2").withFooter("footer 2")});
    list.add(new Object[]{new GroupData().withName("test3").withHeader("header 3").withFooter("footer 3")});
    return list.iterator();
  }

  @Test(dataProvider = "validGroups")
  public void testGroupCreation(GroupData group) throws Exception {
    app.goTO().groupPage();
    Groups befor = app.group().all();
    app.group().create(group);
    assertThat(app.group().count(), equalTo(befor.size() + 1));
    Groups after = app.group().all();
    assertThat(after, equalTo(
            befor.withAdded(group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
   // app.logout();
  }

  @Test
  public void testBedGroupCreation() throws Exception {
    app.goTO().groupPage();
    Groups befor = app.group().all();
    GroupData group = new GroupData().withName("test2'");
    app.group().create(group);
    assertThat(app.group().count(), equalTo(befor.size()));
    Groups after = app.group().all();
    assertThat(after, equalTo(befor));
  }
}
