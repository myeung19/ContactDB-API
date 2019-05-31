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

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<User> findUserByUsername(String userName);

    List<User> findUserBySelfContactId(int selContactId);

    List<User> findUserBySelfContactFirstNameAndSelfContactLastName(String fName, String lName);

    @Query(
            "select u from User u where u.selfContact.firstName = :fName"
    )
    List<User> queryByfirstName(@Param("fName") String fName);

    @Query(
            "select u from User u where u.selfContact.lastName = :lName"
    )
    List<User> queryByLastName(@Param("lName") String lName);

}
