package edu.neumont.csc380.phonedatabse.controller;

import edu.neumont.csc380.contactdatabse.model.Phone;
import edu.neumont.csc380.contactdatabse.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/phone")
public class PhoneRestController
{
    @Autowired
    private PhoneRepository phoneRepo;

    @GetMapping("/{phoneId}")
    public Phone getPhone(@PathVariable int phoneId)
    {
        return phoneRepo.getOne(phoneId);
    }

    @PostMapping
    public void addPhone(@RequestBody Phone newPhone)
    {

    }

    @PutMapping("/{phoneId}")
    public void updateWholePhone(@PathVariable int phoneId, @RequestBody Phone updatedPhone)
    {

    }

    @PatchMapping("/{phoneId}")
    public void updatePartPhone(@PathVariable int phoneId, @RequestBody Phone updatedPhone)
    {

    }

    @DeleteMapping("/{phoneId}")
    public void deletePhone(@PathVariable int phoneId)
    {
        phoneRepo.deleteById(phoneId);
    }

}
