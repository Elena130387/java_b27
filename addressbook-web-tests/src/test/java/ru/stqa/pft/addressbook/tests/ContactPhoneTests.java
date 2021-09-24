package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ContactPhoneTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
    app.contact().goToHome();
    if (app.contact().all().size() == 0) {
      app.contact().create(new ContactData()
              .withFirstname("Sergei").withLastname("Shapoval").withAddress("Spb, Verbnaya st, h.4").withMobilePhone("89554050801").withEmail("8888@rambler.ru").withGroup("test_new"), true);
    }
  }

  @Test
  public void testContactPhones(){
    app.contact().goToHome();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
    assertThat(contact.getHomePhones(), equalTo(cleaned(contactInfoFromEditForm.getHomePhones())));
    assertThat(contact.getMobilePhones(), equalTo(cleaned(contactInfoFromEditForm.getMobilePhones())));
    assertThat(contact.getWorkPhones(), equalTo(cleaned(contactInfoFromEditForm.getWorkPhones())));
  }
  public String cleaned(String phone) {
    return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
  }
}
