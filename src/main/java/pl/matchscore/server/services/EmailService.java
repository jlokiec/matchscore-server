package pl.matchscore.server.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.matchscore.server.models.User;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {
    private Logger logger = LoggerFactory.getLogger(EmailService.class);
    private JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendConfirmationEmail(User user) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setTo(user.getEmail());
            mimeMessageHelper.setReplyTo("matchscore2019@gmail.com");
            mimeMessageHelper.setFrom("MatchScore");
            mimeMessageHelper.setSubject("MatchScore - complete registration");
            String emailContent = confirmationEmailContent(user.getFirstName(), user.getRegistrationId());
            mimeMessageHelper.setText(emailContent, true);
        } catch (MessagingException e) {
            logger.warn("Unable to create MimeMessageHelper.");
        }

        mailSender.send(mimeMessage);
    }

    private String confirmationEmailContent(String name, String uuid) {
        String header = "<h1>Witaj " + name + ",</h1><br />";
        String text = "<p>Aby dokończyć proces rejestracji, kliknij w poniższy link lub wklej go do przeglądarki:</p><br />";
        String url = "<a href=\"http://localhost:8080/api/public/register/confirm?uuid=" + uuid + "\">Complete registration process</a>";
        return header + text + url;
    }
}
