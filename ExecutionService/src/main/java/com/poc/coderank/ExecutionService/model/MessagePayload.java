package com.poc.coderank.ExecutionService.model;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessagePayload implements Serializable {
    private static final long serialVersionUID = 1L;
    private String requestId; // Unique ID for tracking
    private String language;
    private String code;


}
