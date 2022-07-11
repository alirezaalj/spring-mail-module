package ir.alirezaalijani.spring.mail.module.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * My Java Mail properties Config class
 * and Config file must be valid
 */
@Configuration
public class EmailConfiguration {

    @Value("${my-spring.mail.host}")
    private String mailServerHost;
    @Value("${my-spring.mail.port}")
    private Integer mailServerPort;
    @Value("${my-spring.mail.username}")
    private String mailServerUsername;
    @Value("${my-spring.mail.password}")
    private String mailServerPassword;
    @Value("${my-spring.mail.properties.mail.smtp.auth}")
    private String mailServerAuth;
    @Value("${my-spring.mail.properties.mail.smtp.connection-timeout}")
    private long connectionTimeout;
    @Value("${my-spring.mail.properties.mail.smtp.timeout}")
    private long timeout;
    @Value("${my-spring.mail.properties.mail.smtp.write-timeout}")
    private long writeTimeout;
    @Value("${my-spring.mail.properties.mail.smtp.starttls.enable}")
    private String mailServerStartTls;

    /**
     * Module Custom JavaMailSender Bean with my-spring-mail-sender name witch can use
     * in Spring application using @Qualifier("my-spring-mail-sender")
     * @return Bean of JavaMailSender with custom Configs
     */
    @Bean("my-spring-mail-sender")
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(mailServerHost);
        mailSender.setPort(mailServerPort);

        mailSender.setUsername(mailServerUsername);
        mailSender.setPassword(mailServerPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", mailServerAuth);
        props.put("mail.smtp.starttls.enable", mailServerStartTls);
//        props.put("mail.debug", "true");
        props.put("mail.smtp.connectiontimeout",connectionTimeout);
        props.put("mail.smtp.timeout",timeout);
        props.put("mail.smtp.writetimeout",writeTimeout);
        return mailSender;
    }
}
