package edu.neumont.csc380.contactdatabse.controller;

import edu.neumont.csc380.contactdatabse.exception.UserNotFoundException;
import edu.neumont.csc380.contactdatabse.model.User;
import edu.neumont.csc380.contactdatabse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminRestController
{
    @Autowired
    private UserRepository userRepo;

    @PostMapping("/setRole")
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public void setRole(@RequestBody Map<String, Object> attributes)
    {
        User user = userRepo.findUserByUsername((String)attributes.get("username"))
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        boolean isSetAdmin = (boolean)attributes.get("admin");
        if(user.getRoles().contains(User.Role.ADMIN) && isSetAdmin)
        {
            return;
        }
        else if(user.getRoles().contains(User.Role.ADMIN) && !isSetAdmin)
        {
            user.getRoles().remove(User.Role.ADMIN);
        }
        else if(!user.getRoles().contains(User.Role.ADMIN) && isSetAdmin)
        {
            user.getRoles().add(User.Role.ADMIN);
        }
        userRepo.save(user);
    }
}