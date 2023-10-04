package com.example.demo.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CertificateInformation {
    private String issuedTo;
    private String validationDate;
    private String expirationDate;
    private String remainingTime;
}