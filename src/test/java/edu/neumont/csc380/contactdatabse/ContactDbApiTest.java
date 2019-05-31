package edu.neumont.csc380.contactdatabse;

import edu.neumont.csc380.contactdatabse.controller.AdminRestController;
import edu.neumont.csc380.contactdatabse.controller.UserRestContoller;
import edu.neumont.csc380.contactdatabse.model.Contact;
import edu.neumont.csc380.contactdatabse.model.Phone;
import edu.neumont.csc380.contactdatabse.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

public class ContactDbApiTest
{
    private UserRestContoller userRestContoller;
    private AdminRestController adminRestController;
//
    @Before
    public void setUp()
    {
        userRestContoller = new UserRestContoller();
        adminRestController = new AdminRestController();

//        UserRestContoller.user = new User(1, new Contact(1, "Anne", "Smith", new ArrayList<>(), ""), new ArrayList<>());
//        userRestContoller.createContact(new Contact(0, "Bob", "Johnson", Arrays.asList(new Phone(Phone.PhoneType.Personal, "12341234")), ""));
    }

    @After
    public void clearUp()
    {
        userRestContoller = null;
        adminRestController = null;
    }

    @Test
    public void contactGet()
    {
        int size = userRestContoller.getUser(6).getContacts().size();
        assertEquals(2, size);
    }
//
    @Test
    public void contactPost()
    {
        Contact c = new Contact(1, "Emily", "Gibson", Arrays.asList(new Phone(Phone.PhoneType.Personal, "12341234")), "");
        User u = userRestContoller.getUser(6);
//        userRestContoller.
//        assertEquals(userRestContoller.getAllContacts().get(1), c);
    }
//
//    @Test
//    public void contactUpdate()
//    {
//        Contact c = new Contact(0, "Mary", "Johnson", Arrays.asList(new Phone(Phone.PhoneType.Personal, "12341234")), "");
//        userRestContoller.updateContact(0, c);
//        assertEquals(userRestContoller.getContact(0).getFirstName(), "Mary");
//    }
//
//    @Test
//    public void contactDelete()
//    {
//        userRestContoller.deleteContact(0);
//        int size = userRestContoller.getAllContacts().size();
//        assertEquals(0, size);
//    }
//
//    @Test
//    public void admineGet()
//    {
//        assertEquals(adminRestController.getUser(), UserRestContoller.user);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void adminPost()
//    {
//        adminRestController.createUser(new User());
//    }
//
//    @Test
//    public void adminUpdate()
//    {
//        User user = new User(1, new Contact(1, "Diana", "Smith", new ArrayList<>(), ""), new ArrayList<>());
//        adminRestController.updateUser(user);
//        assertEquals(adminRestController.getUser(), user);
//    }
//
//    @Test
//    public void adminDelete()
//    {
//        adminRestController.deleteUser();
//        assertEquals(null, adminRestController.getUser());
//    }
}
