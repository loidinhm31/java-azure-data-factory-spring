package com.example.demo.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EmailBuilder {
    List<CertificateInformation> certificateInformationList;
}
