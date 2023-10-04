package com.example.demo.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailBean {
    private String from;
    private String to;
    private String cc;
    private String emailSubject;
    private String emailBody;
}
