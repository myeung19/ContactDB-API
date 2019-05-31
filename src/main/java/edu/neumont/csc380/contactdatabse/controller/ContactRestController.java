package edu.neumont.csc380.contactdatabse.controller;

import edu.neumont.csc380.contactdatabse.repository.ContactRepository;
import edu.neumont.csc380.contactdatabse.model.Contact;
import edu.neumont.csc380.contactdatabse.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/contact")
public class ContactRestController
{
    @Autowired
    private ContactRepository contactRepo;

    @Autowired
    private UserRestContoller userRestContoller;

    @GetMapping("/{userId}/{contactId}")
    public Contact getContact(@PathVariable int contactId)
    {
        return contactRepo.getOne(contactId);
    }

    @GetMapping()
    public List<Contact> getContacts()
    {
        User user = userRestContoller.getUser(6);
        return user.getContacts();
    }

    @PostMapping()
    public void addContact(@RequestBody Contact newContact)
    {
        User user = userRestContoller.getUser(6);
        user.getContacts().add(newContact);
        userRestContoller.updateWholeUser(user.getId(), user);
    }

    @PutMapping("/{contactId}")
    public void updateWholeContact(@PathVariable int contactId, @RequestBody Contact updatedContact)
    {
        User user = userRestContoller.getUser(6);
        Contact tempC = user.getContacts().stream().filter(c -> c.getId() == contactId).findFirst().get();
        int index = user.getContacts().indexOf(tempC);
        user.getContacts().set(index, updatedContact);
        userRestContoller.updateWholeUser(user.getId(), user);
}

    @PatchMapping("/{contactId}")
    public void updatePartContact(@PathVariable int contactId, @RequestBody Contact updatedContact)
    {
        User user = userRestContoller.getUser(6);
        Contact tempC = user.getContacts().stream().filter(c -> c.getId() == updatedContact.getId()).findFirst().get();
        int index = user.getContacts().indexOf(tempC);
        user.getContacts().set(index, updatedContact);
        userRestContoller.updateWholeUser(user.getId(), user);
    }

    @DeleteMapping("/{contactId}")
    public void deleteContact(@PathVariable int contactId)
    {
        User user = userRestContoller.getUser(6);
        Contact contact = user.getContacts().stream().filter(c -> c.getId() == contactId).findFirst().get();
        user.getContacts().remove(contact);
        userRestContoller.updateWholeUser(user.getId(), user);
    }
}
