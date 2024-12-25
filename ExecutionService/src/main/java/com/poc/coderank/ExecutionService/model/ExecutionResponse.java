package com.poc.coderank.ExecutionService.model;

import java.io.Serializable;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExecutionResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private String requestId; // Unique ID for tracking
    private CodeResponse request;
    private long executionTime; // Mock execution time in ms
    private String status; // Mock status
}

