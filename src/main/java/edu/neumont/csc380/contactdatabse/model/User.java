package edu.neumont.csc380.contactdatabse.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User implements UserDetails
{
    public enum Role {
        USER, ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    private Contact selfContact;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Contact> contacts;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Fetch(FetchMode.SELECT)
    private List<Role> roles;

    public User()
    {}

    public User(Contact selfContact, List<Contact> contacts, String username, String email, String password, List<Role> roles)
    {
        this.selfContact = selfContact;
        this.contacts = contacts;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Contact getSelfContact()
    {
        return selfContact;
    }

    public void setSelfContact(Contact selfContact)
    {
        this.selfContact = selfContact;
    }

    public List<Contact> getContacts()
    {
        return contacts;
    }

    public void setContacts(List<Contact> contacts)
    {
        this.contacts = contacts;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public List<Role> getRoles()
    {
        return roles;
    }

    public void setRoles(List<Role> roles)
    {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return this.getRoles().stream()
                .map(role -> new GrantedAuthority()
                {
                    @Override
                    public String getAuthority()
                    {
                        return role.toString();
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }
    @Override
    public boolean isEnabled()
    {
        return true;
    }

    @Override
    public String toString()
    {
        return "User{" +
                "id=" + id +
                ", selfContact=" + selfContact +
                ", contacts=" + contacts +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
