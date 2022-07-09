package ir.alirezaalijani.spring.mail.module.mail;

public class MailSendException extends RuntimeException{
    public MailSendException() {
        super();
    }

    public MailSendException(String message) {
        super(message);
    }

    public MailSendException(String message, Throwable cause) {
        super(message, cause);
    }
}
