package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;


public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContacts() throws IOException {
    List<Object[]> list = new ArrayList<Object[]>();
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.crv")));
    String line = reader.readLine();
    while (line != null){
      String[] split = line.split(";");
      list.add(new Object[] {new ContactData().withFirstname(split[0]).withLastname(split[1]).
              withAddress(split[2]).withEmail(split[3]).withHomePhone(split[4])
              .withGroup(split[5])});
      line = reader.readLine();
    }
    return list.iterator();
  }

  @Test(dataProvider = "validContacts")
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

  @Test
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
