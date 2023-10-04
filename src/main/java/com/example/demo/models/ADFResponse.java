package com.example.demo.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ADFResponse {
    private String runId;
    private String status;
}
