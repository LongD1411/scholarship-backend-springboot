package com.scholarship.service.impl;

import com.scholarship.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    public void sendVerificationEmail(String toEmail, String verificationUrl) {
        String subject = "Xác minh tài khoản của bạn";
        String content = "<p>Xin chào,</p>"
                + "<p>Cảm ơn bạn đã đăng ký tài khoản. Vui lòng nhấn vào link bên dưới để xác minh email của bạn:</p>"
                + "<p><a href=\"" + verificationUrl + "\">Xác minh tài khoản</a></p>"
                + "<p>Link này sẽ hết hạn sau 15 phút.</p>"
                + "<p>Nếu bạn không yêu cầu đăng ký, hãy bỏ qua email này.</p>";
        sendHtmlEmail(toEmail, subject, content);
    }

    public void sendHtmlEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Lỗi khi gửi email: " + e.getMessage());
        }
    }
}
