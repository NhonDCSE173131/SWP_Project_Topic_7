package com.product.server.koi_control_application.serviceInterface;

public interface IEmailService  {
    void sendMail( String to, String subject, String text);
    void sendPasswordToEmail(String email, String newPassword);
}
