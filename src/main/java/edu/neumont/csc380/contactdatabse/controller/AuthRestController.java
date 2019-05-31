package edu.neumont.csc380.contactdatabse.controller;

import edu.neumont.csc380.contactdatabse.model.User;
import edu.neumont.csc380.contactdatabse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/")
public class AuthRestController
{
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserRestContoller userRestContoller;

    @PostMapping("/register")
    public void register(@RequestBody User user)
    {
        user.setRoles(Collections.singletonList(User.Role.USER));
        userRepo.save(user);
    }

    @PostMapping("/reset")
    public void reset()
    {

    }
}
