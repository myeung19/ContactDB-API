package edu.neumont.csc380.contactdatabse.controller;

import edu.neumont.csc380.contactdatabse.exception.UserNotFoundException;
import edu.neumont.csc380.contactdatabse.model.Contact;
import edu.neumont.csc380.contactdatabse.model.dto.UserDTO;
import edu.neumont.csc380.contactdatabse.repository.ContactRepository;
import edu.neumont.csc380.contactdatabse.repository.UserRepository;
import edu.neumont.csc380.contactdatabse.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserRestContoller
{
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ContactRepository contactRepo;

    @GetMapping
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getAllUser()
    {
        return userRepo.findAll();
    }

    @GetMapping("/{username}")
    @Transactional
    @PreAuthorize("hasAuthority('USER', 'ADMIN')")
    public UserDTO getUser(@PathVariable String username)
    {
        return userRepo.findUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"))
                .getUserDTO();
    }

    @PostMapping("i")
    public ResponseEntity<byte[]> getCourseImage(
            @RequestPart("file") MultipartFile file,
            HttpServletRequest request) throws IOException {

        System.out.println(request.getRequestURL());

        byte[] i = file.getBytes();
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(i);
    }

    @PatchMapping("/{username}")
    @Transactional
    @PreAuthorize("hasAuthority('USER', 'ADMIN')")
    public void updateUserInfo(@PathVariable String username, @RequestBody Contact updatedContact)
    {
        User user = userRepo.findUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Contact selfContact = null;

        if(user.getSelfContact() == null)
        {
            selfContact = new Contact();
            user.setSelfContact(selfContact);
        }

        selfContact.setFirstName(updatedContact.getFirstName());
        selfContact.setLastName(updatedContact.getLastName());
        selfContact.setPhones(updatedContact.getPhones());
        selfContact.setNote(updatedContact.getNote());

        contactRepo.save(selfContact);
        userRepo.save(user);
    }

    @DeleteMapping("/{userId}")
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteUser(@PathVariable int userId)
    {
        userRepo.deleteById(userId);
    }
}