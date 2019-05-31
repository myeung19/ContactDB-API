package edu.neumont.csc380.contactdatabse.model;

import javax.persistence.*;

@Entity
@Table(name = "phones")
public class Phone
{
    public enum PhoneType
    {
        Mobile, Work, Personal, Home
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int phoneId;
    private PhoneType type;
    private String number;

    public Phone(PhoneType type, String number)
    {
        this.setType(type);
        this.setNumber(number);
    }

    public Phone()
    {
    }

    public PhoneType getType()
    {
        return type;
    }

    public void setType(PhoneType type)
    {
        this.type = type;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    @Override
    public String toString()
    {
        return "Phone{" +
                "type=" + type +
                ", number='" + number + '\'' +
                '}';
    }
}
