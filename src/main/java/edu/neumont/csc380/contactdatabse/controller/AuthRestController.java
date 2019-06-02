package edu.neumont.csc380.contactdatabse.controller;

import edu.neumont.csc380.contactdatabse.model.User;
import edu.neumont.csc380.contactdatabse.repository.UserRepository;
import edu.neumont.csc380.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
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

    @PostMapping("/register")
    public void register(@RequestBody User user)
    {
        user.setRoles(Collections.singletonList(User.Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    @PostMapping("/changePassword")
    @PreAuthorize("hasAuthority('USER', 'ADMIN')")
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
    public void reset(
            @RequestBody Map<String, Object> attributes) throws MessagingException
    {
        String newPassword = passwordEncoder.encode(Utils.generateRandomPassword());
        String userEmail = (String) attributes.get("email");

        Optional<User> optionalUser = userRepo.findUserByEmail(userEmail);
        if (optionalUser.isPresent())
        {
            User user = optionalUser.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepo.save(user);
        }
        else
        {
            throw new IllegalArgumentException();
        }

        emailSender.send(
                Utils.draftEmailWithPassword(
                        emailSender,
                        from,
                        userEmail,
                        "Reset your password",
                        newPassword));
    }
}
