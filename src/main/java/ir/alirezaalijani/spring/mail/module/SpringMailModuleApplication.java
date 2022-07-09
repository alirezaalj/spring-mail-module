package ir.alirezaalijani.spring.mail.module;

import ir.alirezaalijani.spring.mail.module.mail.MailService;
import ir.alirezaalijani.spring.mail.module.mail.model.TextMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringMailModuleApplication implements CommandLineRunner {

    @Autowired
    private MailService mailService;

    public static void main(String[] args) {
        SpringApplication.run(SpringMailModuleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
