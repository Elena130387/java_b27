package ru.stqa.pft.addressbook.tests;

import com.thoughtworks.xstream.XStream;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase{

  @DataProvider
  public Iterator<Object[]> validGroups() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groups.xml")));
    String xml = "";
    String line = reader.readLine();
    while (line != null){
      xml += line;
      line = reader.readLine();
    }
    XStream xstream = new XStream();
    xstream.processAnnotations(GroupData.class);
    List<GroupData> groups = (List<GroupData>) xstream.fromXML(xml);
    return groups.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
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
