package edu.neumont.csc380.contactdatabse.controller;

import edu.neumont.csc380.contactdatabse.exception.UserAlreadyExistException;
import edu.neumont.csc380.contactdatabse.model.User;
import edu.neumont.csc380.contactdatabse.repository.UserRepository;
import edu.neumont.csc380.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/")
public class AuthRestController
{
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String from;

    @GetMapping("/")
    public String getHomeContent()
    {
        return "API currently working";
    }

    @PostMapping("/login")
    @Transactional
    public void login() { }

    @PostMapping("/register")
    @Transactional
    public void register(@RequestBody User newUser)
    {
        Optional<User> optionalUser = userRepo.findUserByUsername(newUser.getUsername());
        if(optionalUser.isPresent())
            throw new UserAlreadyExistException("User " + newUser.getUsername() + " already exists");

        newUser.setRoles(Collections.singletonList(User.Role.USER));
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepo.save(newUser);
    }

    @PostMapping("/changePassword")
    @PreAuthorize("hasAuthority('USER', 'ADMIN')")
    @Transactional
    public void changePassword(
            Principal p,
            @RequestBody Map<String, String> attributes)
    {
        Optional<User> optionalUser = userRepo.findUserByUsername(p.getName());
        if (optionalUser.isPresent())
        {
            User user = optionalUser.get();
            if(passwordEncoder.matches(attributes.get("oldPassword"), user.getPassword()))
            {
                user.setPassword(passwordEncoder.encode(attributes.get("newPassword")));
                userRepo.save(user);
            }
        }
    }

    @PostMapping("/reset")
    @Transactional
    public void reset(
            @RequestBody Map<String, Object> attributes) throws MessagingException
    {
        String newPassword = Utils.generateRandomPassword();
        String userEmail = (String) attributes.get("email");

        Optional<User> optionalUser = userRepo.findUserByEmail(userEmail);
        if (optionalUser.isPresent())
        {
            User user = optionalUser.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepo.save(user);

            emailSender.send(
                    Utils.draftEmailWithPassword(
                            emailSender,
                            from,
                            userEmail,
                            "Reset your password",
                            newPassword));
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }
}
