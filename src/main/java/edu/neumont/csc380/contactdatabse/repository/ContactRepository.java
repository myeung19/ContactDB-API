package edu.neumont.csc380.contactdatabse.repository;

import edu.neumont.csc380.contactdatabse.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Integer>
{
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Contact getOne(Integer id);

    List<Contact> findContactByFirstName(String firstName);

    List<Contact> findContactByLastName(String lastName);

    @Query(
            "select c from Contact c where c.firstName = :fName and c.lastName = :lName"
    )
    List<Contact> queryByFirstNameAndLastName(@Param("fName") String fName, @Param("lName") String lName);

//    @Query(
//            "select c from Contact c where c.phones.type = 'Work'"
//    )
//    List<Contact> queryByPhoneType();
}
