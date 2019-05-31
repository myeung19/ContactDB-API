package edu.neumont.csc380.contactdatabse.repository;

import edu.neumont.csc380.contactdatabse.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;

public interface PhoneRepository extends JpaRepository<Phone, Integer>
{
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Phone getOne(Integer id);

    List<Phone> findPhoneByType(Phone.PhoneType type);

    @Query(
            "Select p From Phone p where p.number like :areaCode%"
    )
    List<Phone> queryByAreaCode(@Param("areaCode") String areaCode);

    @Query(
            "Select p From Phone p where length(p.number) = 9"
    )
    List<Phone> queryByNumberLength();
}
