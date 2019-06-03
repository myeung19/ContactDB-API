package edu.neumont.csc380.contactdatabse.model;

public class ErrorResponse
{
    private int code;
    private String message;

    public ErrorResponse(int code, String message)
    {
        this.setCode(code);
        this.setMessage(message);
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
