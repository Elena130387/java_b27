package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import org.openqa.selenium.json.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  public Iterator<Object[]> validGroupsFromXml() throws IOException {
   try(BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groups.xml")))) {
     String xml = "";
     String line = reader.readLine();
     while (line != null) {
       xml += line;
       line = reader.readLine();
     }
     XStream xstream = new XStream();
     xstream.allowTypes(new Class[]{GroupData.class});
     xstream.processAnnotations(GroupData.class);
     List<GroupData> groups = (List<GroupData>) xstream.fromXML(xml);
     return groups.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
   }
  }

  @DataProvider
  public Iterator<Object[]> validGroupsFromJson() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groups.json")))) {
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += line;
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<GroupData> groups = gson.fromJson(json, new TypeToken<List<GroupData>>() {
      }.getType());
      return groups.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }

  @Test(dataProvider = "validGroupsFromXml")
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

  @Test(enabled = false)
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
