package edu.neumont.csc380.contactdatabse.exception;

public class UserAlreadyExistException extends RuntimeException
{
    public UserAlreadyExistException(String message)
    {
        super(message);
    }
}
