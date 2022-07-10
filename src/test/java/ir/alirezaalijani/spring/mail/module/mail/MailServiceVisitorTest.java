package ir.alirezaalijani.spring.mail.module.mail;

import ir.alirezaalijani.spring.mail.module.mail.model.BasicMailMessage;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;

import javax.mail.internet.MimeMessage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("mailtest")
@SpringBootTest
class MailServiceVisitorTest {

    @MockBean
    MailService mailService;

    @SpyBean
    JavaMailSender javaMailSender;

    @Autowired
    MailMessageVisitor mailServiceVisitor;

    @Captor
    ArgumentCaptor<Boolean> booleanArgumentCaptor;

    @Test
    void acceptTextMailTestWithError(){
        doNothing().when(mailService).mailSend(any(BasicMailMessage.class),booleanArgumentCaptor.capture());
        doThrow(new MailSendException("Some dummy error")).when(javaMailSender).send(any(MimeMessage.class));
        MailServiceTest.createTextMail().accept(mailServiceVisitor);
        MailServiceTest.createHtmlMail().accept(mailServiceVisitor);
        MailServiceTest.createTextMailWithAttachment().accept(mailServiceVisitor);
        MailServiceTest.createHtmlMailWithAttachment().accept(mailServiceVisitor);
        verify(javaMailSender,times(12)).send(any(MimeMessage.class));
        assertThat(booleanArgumentCaptor.getAllValues()).hasSize(4);
        assertThat(booleanArgumentCaptor.getAllValues().contains(true)).isFalse();
    }

    @Test
    void acceptTextMailTest(){
        doNothing().when(mailService).mailSend(any(BasicMailMessage.class),booleanArgumentCaptor.capture());
        doNothing().when(javaMailSender).send(any(MimeMessage.class));
        MailServiceTest.createTextMail().accept(mailServiceVisitor);
        MailServiceTest.createHtmlMail().accept(mailServiceVisitor);
        MailServiceTest.createTextMailWithAttachment().accept(mailServiceVisitor);
        MailServiceTest.createHtmlMailWithAttachment().accept(mailServiceVisitor);

        verify(javaMailSender,times(4)).send(any(MimeMessage.class));
        assertThat(booleanArgumentCaptor.getAllValues()).hasSize(4);
        assertThat(booleanArgumentCaptor.getAllValues().contains(false)).isFalse();
    }
}