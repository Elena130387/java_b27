package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import org.openqa.selenium.json.TypeToken;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;


public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContactsFromXml() throws IOException {
   try(BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.xml")))) {
     String xml = "";
     String line = reader.readLine();
     while (line != null) {
       xml += line;
       line = reader.readLine();
     }
     XStream xstream = new XStream();
     xstream.allowTypes(new Class[]{ContactData.class});
     xstream.processAnnotations(ContactData.class);
     List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml);
     return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
   }
  }

  @DataProvider
  public Iterator<Object[]> validContactsFromJson() throws IOException {
   try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))) {
     String json = "";
     String line = reader.readLine();
     while (line != null) {
       json += line;
       line = reader.readLine();
     }
     Gson gson = new Gson();
     List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {
     }.getType());
     return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
   }
  }

  @Test(dataProvider = "validContactsFromXml")
  public void testContactCreation(ContactData contact) throws Exception {
    app.contact().goToHome();
    Contacts befor = app.contact().all();
    File photo = new File("src/test/resources/bug.png");
   // ContactData contact = new ContactData()
     //       .withFirstname("Elena").withLastname("Shapoval").withAddress("Spb, Verbnaya st, h.4").withHomePhone("14141")
       //     .withMobilePhone("89554050801").withWorkPhone("7898").withEmail("8888@rambler.ru").
         //   withPhoto(photo).withGroup("test_new");
    app.contact().create(contact, true);
    assertThat(app.contact().count(), equalTo(befor.size() + 1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(befor.withAdded(
            contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
   // app.logout();
  }

  @Test(enabled = false)
  public void testContactBedCreation() throws Exception {
    app.contact().goToHome();
    Contacts befor = app.contact().all();
    ContactData contact = new ContactData()
            .withFirstname("Elena").withLastname("Shapoval").withAddress("Spb, Verbnaya st, h.4").withHomePhone("14141")
            .withMobilePhone("89554050801").withWorkPhone("7898").withEmail("8888@rambler.ru").withGroup("test_new");
    app.contact().create(contact, true);
    assertThat(app.contact().count(), equalTo(befor.size()));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(befor));
  }
}
