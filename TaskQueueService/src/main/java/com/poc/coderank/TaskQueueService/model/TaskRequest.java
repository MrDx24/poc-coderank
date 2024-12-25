package com.poc.coderank.TaskQueueService.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TaskRequest {
    private String language;
    private String code;
}
