package pl.powiescdosukcesu.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Async
    //TODO
    public void sendEmail(String to,String subject,String text,boolean isHtml) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

        message.setTo(to);
        message.setFrom("test");
        message.setSubject("Rejestracja");
        message.setText("test",isHtml);
        javaMailSender.send(mimeMessage);

    }
}
