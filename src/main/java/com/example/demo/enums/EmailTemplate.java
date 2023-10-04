package com.example.demo.enums;

public enum EmailTemplate {
    EMAIL_CA(
            "Certificate Information",
            "cert_template.html"
    );

    public final String baseSubject;
    public final String templateFileName;

    EmailTemplate(String baseSubject, String templateFileName) {
        this.baseSubject = baseSubject;
        this.templateFileName = templateFileName;
    }
}
