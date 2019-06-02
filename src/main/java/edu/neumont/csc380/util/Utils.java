package edu.neumont.csc380.util;

import com.sun.mail.smtp.SMTPMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.activation.MimetypesFileTypeMap;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Collections;

public class Utils
{
    public static String getTypeFromFileName(String fileName)
    {
        return new MimetypesFileTypeMap().getContentType(fileName);
    }

    public static MimeMessage draftEmailWithPassword(
            JavaMailSender emailSender, String from, String to, String subject, String resetedPassword) throws MessagingException
    {
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText("Hey " + to + ",\n\nYour new password is " + resetedPassword + ".\n\nBest regard." );

        return message;
    }

    public static String generateRandomPassword()
    {
        String chars = "1234567890qwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM!@#$%^&*()_+-={}|[]\\:\";''<>?`~";
        Integer[] ranNums = new Integer[chars.length()];
        for (int i = 0; i < ranNums.length; i++) {
            ranNums[i] = i;
        }
        Collections.shuffle(Arrays.asList(ranNums));
        System.out.println(Arrays.toString(ranNums));

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++)
        {
            sb.append(chars.toCharArray()[ranNums[i]]);
        }
        return sb.toString();
    }
}
