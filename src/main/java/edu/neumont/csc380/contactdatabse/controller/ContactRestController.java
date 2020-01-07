package edu.neumont.csc380.contactdatabse.controller;

import edu.neumont.csc380.contactdatabse.model.Contact;
import edu.neumont.csc380.contactdatabse.repository.ContactRepository;
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
        return null;
    }

    @GetMapping()
    public List<Contact> getContacts()
    {
        return null;
    }

    @PostMapping()
    public void addContact(@RequestBody Contact newContact)
    {

    }

    @PutMapping("/{contactId}")
    public void updateWholeContact(@PathVariable int contactId, @RequestBody Contact updatedContact)
    {

    }

    @PatchMapping("/{contactId}")
    public void updatePartContact(@PathVariable int contactId, @RequestBody Contact updatedContact)
    {

    }

    @DeleteMapping("/{contactId}")
    public void deleteContact(@PathVariable int contactId)
    {

    }
}
