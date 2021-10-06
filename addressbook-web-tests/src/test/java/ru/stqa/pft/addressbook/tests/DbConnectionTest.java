package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.sql.*;

public class DbConnectionTest {

  @Test
  public void testDbConnection() {
    Connection conn = null;
    try {
      conn = DriverManager.getConnection("jdbc:mysql://localhost/addressbook?user=root&password=");
      Statement st = conn.createStatement();
      Statement st_cont = conn.createStatement();
      ResultSet rs_gr = st.executeQuery("select group_id, group_name, group_header, group_footer from group_list");
      ResultSet rs_cont = st_cont.executeQuery("select id, lastname, firstname, address, home, mobile, work, email, email2, " +
              "email3, photo from addressbook");
      Groups groups = new Groups();
      Contacts contacts = new Contacts();
      while (rs_gr.next()){
       groups.add(new GroupData().withId(rs_gr.getInt("group_id")).withName(rs_gr.getString("group_name")).
        withHeader(rs_gr.getString("group_header")).withFooter(rs_gr.getString("group_footer")));
      }
      rs_gr.close();
      st.close();

      while (rs_cont.next()){
        contacts.add(new ContactData().withId(rs_cont.getInt("id")).withLastname(rs_cont.getString("lastname"))
                .withFirstname(rs_cont.getString("firstname")).withAddress(rs_cont.getString("address"))
                .withHomePhone(rs_cont.getString("home")).withMobilePhone(rs_cont.getString("mobile"))
                .withWorkPhone(rs_cont.getString("work")).withEmail(rs_cont.getString("email"))
                .withEmail2(rs_cont.getString("email2")).withEmail3(rs_cont.getString("email3")));
      }
      rs_cont.close();
      st_cont.close();
      conn.close();
      System.out.println(groups);
      System.out.println(contacts);
    } catch (SQLException ex) {
      // handle any errors
      System.out.println("SQLException: " + ex.getMessage());
      System.out.println("SQLState: " + ex.getSQLState());
      System.out.println("VendorError: " + ex.getErrorCode());
    }
  }
}
