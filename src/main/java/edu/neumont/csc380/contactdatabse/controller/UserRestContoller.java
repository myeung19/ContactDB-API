package edu.neumont.csc380.contactdatabse.controller;

import edu.neumont.csc380.contactdatabse.repository.UserRepository;
import edu.neumont.csc380.contactdatabse.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    @Transactional
    public List<User> getAllUser()
    {
        return userRepo.findAll();
    }

    @GetMapping("/{userId}")
    @Transactional
    public User getUser(@PathVariable int userId)
    {
        return userRepo.getOne(userId);
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

    @PostMapping
    @Transactional
    public void addUser(@RequestBody User newUser)
    {
        userRepo.save(newUser);
    }

    @PutMapping("/{userId}")
    @Transactional
    public void updateWholeUser(@PathVariable int userId, @RequestBody User updatedUser)
    {

    }

    @PatchMapping("/{userId}")
    @Transactional
    public void updatePartUser(@PathVariable int userId, @RequestBody User updatedUser)
    {

    }

    @DeleteMapping("/{userId}")
    @Transactional
    public void deleteUser(@PathVariable int userId)
    {
        userRepo.deleteById(userId);
    }

}