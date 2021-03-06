package generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import org.hibernate.Session;
import org.hibernate.SessionBuilder;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {

  @Parameter(names = "-c", description = "Contact count")
  public int count;

  @Parameter (names = "-f", description = "Target file")
  public String file;

  @Parameter (names = "-d", description = "Data format")
  public String format;
  private SessionBuilder<SessionBuilder> sessionFactory;


  public static void main(String[] args) throws IOException {
    ContactDataGenerator generator = new ContactDataGenerator();
    JCommander jCommander = new JCommander(generator);
    try {
      jCommander.parse(args);
    } catch (ParameterException ex) {
      jCommander.usage();
      return;
    }
    generator.run();
  }

  private void run() throws IOException {
    List<ContactData> contacts = generateContacts(count);
    if (format.equals("csv")){
      saveAsCsv(contacts, new File (file));
    } else if (format.equals("xml")){
      saveAsXml(contacts, new File (file));
    } else if (format.equals("json")){
      saveAsJson(contacts, new File (file));
    } else {
      System.out.println("Недоступный формат " + format);
    }
  }

  private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    String json = gson.toJson(contacts);
    try ( Writer writer = new FileWriter(file)){
      writer.write(json);
    }
  }

  private void saveAsXml(List<ContactData> contacts, File file) throws IOException {
    XStream xstream = new XStream();
    xstream.processAnnotations(ContactData.class);
    String xml = xstream.toXML(contacts);
    try (Writer writer = new FileWriter(file)){
      writer.write(xml);
    }
  }

  private static void saveAsCsv(List<ContactData> contacts, File file) throws IOException {
   try (Writer writer = new FileWriter(file)) {
     for (ContactData contact : contacts) {
       writer.write(String.format
               ("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\n", contact.getFirstname(), contact.getLastname(),
                       contact.getAddress(), contact.getEmail(), contact.getEmail2(), contact.getEmail3(),
                       contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone(), contact.getPhoto()));
     }
   }
  }

  private  List<ContactData> generateContacts(int count) {
    List<ContactData> contacts = new ArrayList<ContactData>();
    for (int i = 0; i < count; i++){
      contacts.add(new ContactData().withFirstname(String.format("Elena_%s", i)).withLastname(String.format("Shapoval_%s", i))
              .withAddress(String.format("Spb, Verbnaya st, h. %s", i)).withEmail(String.format("Elena_%s@rambler.ru", i))
              .withHomePhone(String.format("188%s", i)).withMobilePhone("").withWorkPhone("")
                      .withEmail2("").withEmail3("")
              .withPhoto(new File("src/test/resources/bug.png")));
    }
    return contacts;
  }
}
