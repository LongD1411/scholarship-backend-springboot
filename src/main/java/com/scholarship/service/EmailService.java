package com.scholarship.service;

public interface EmailService {
    void sendVerificationEmail(String toEmail, String verificationUrl);
    void sendHtmlEmail(String to, String subject, String htmlContent);
}
