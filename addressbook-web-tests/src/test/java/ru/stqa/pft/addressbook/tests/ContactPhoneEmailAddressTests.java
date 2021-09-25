package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneEmailAddressTests extends TestBase{
  @BeforeMethod
  public void ensurePreconditions(){
    app.contact().goToHome();
    if (app.contact().all().size() == 0) {
      app.contact().create(new ContactData()
              .withFirstname("Sergei").withLastname("Shapoval").withAddress("Spb, Verbnaya st, h.4")
              .withMobilePhone("89554050801").withEmail("8888@rambler.ru").withGroup("test_new")
              .withEmail2("714@rambler.ru"), true);
    }
  }

  @Test
  public void testContactPhoneEmailAddress(){
    app.contact().goToHome();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
    assertThat(contact.getAddress(),equalTo(contactInfoFromEditForm.getAddress()));
    assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
    assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
  }

  private String mergePhones(ContactData contact) {
    return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone())
            .stream().filter((s) -> ! s.equals(""))
            .map(ContactPhoneEmailAddressTests::cleanedPhone)
            .collect(Collectors.joining("\n"));
  }

  public static String cleanedPhone(String phone) {
    return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
  }

  private String mergeEmails(ContactData contact) {
    return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
            .stream().filter((s) -> ! s.equals(""))
            .map(ContactPhoneEmailAddressTests::cleanedEmail)
            .collect(Collectors.joining("\n"));
  }

  public static String cleanedEmail(String email) {
    return email.replaceAll("\\s", "").replaceAll("['()]", "");
  }

}
