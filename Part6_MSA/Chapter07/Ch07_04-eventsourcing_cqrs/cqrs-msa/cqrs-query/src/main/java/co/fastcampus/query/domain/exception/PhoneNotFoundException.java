package co.fastcampus.query.domain.exception;

public class PhoneNotFoundException extends Exception{
    private String message;
    private String name;

    public PhoneNotFoundException(String name, String message) {
        super(message);
        this.name = name;
    }
}
