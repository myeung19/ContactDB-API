package edu.neumont.csc380.contactdatabse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ContactDbApi extends SpringBootServletInitializer
{
    public static void main(String[] args)
    {
        SpringApplication.run(ContactDbApi.class, args);
    }
}
