package edu.neumont.csc380.contactdatabse.model.dto;

import edu.neumont.csc380.contactdatabse.model.Contact;
import edu.neumont.csc380.contactdatabse.model.User;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO
{
    private Contact selfContact;
    private List<Contact> contacts;
    private String username;
    private String email;
    private List<User.Role> roles;

    public UserDTO(Contact selfContact, List<Contact> contacts, String username, String email, List<User.Role> roles)
    {
        this.selfContact = selfContact;
        this.contacts = contacts;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}

