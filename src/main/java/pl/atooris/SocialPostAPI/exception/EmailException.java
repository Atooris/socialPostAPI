package pl.atooris.SocialPostAPI.exception;

public class EmailException extends RuntimeException{
    public EmailException(){
        super("Error while sending email");
    }
}
