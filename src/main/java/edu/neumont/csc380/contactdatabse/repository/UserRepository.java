package edu.neumont.csc380.contactdatabse.repository;

import edu.neumont.csc380.contactdatabse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>
{
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    User getOne(Integer id);

    Optional<User> findUserByUsername(String userName);

    Optional<User> findUserByEmail(String email);

    List<User> findUserBySelfContactId(int selContactId);

    List<User> findUserBySelfContactFirstNameAndSelfContactLastName(String fName, String lName);

}
