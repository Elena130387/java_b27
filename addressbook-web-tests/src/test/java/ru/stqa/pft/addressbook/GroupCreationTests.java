package ru.stqa.pft.addressbook;

import org.testng.annotations.*;

public class GroupCreationTests extends TestBase{

  @Test
  public void testGroupCreation() throws Exception {
    gotoGroupPage();
    initGroupCreation();
    fillGroupForm(new GroupData("test_new", "test_new1", "test_new2"));
    submitGroupCreation();
    returnToGroupPage();
    logout();
  }

}
