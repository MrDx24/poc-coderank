package com.poc.coderank.ExecutionService.service;

import com.poc.coderank.ExecutionService.model.CodeResponse;
import com.poc.coderank.ExecutionService.model.ExecutionResponse;
import com.poc.coderank.ExecutionService.model.MessagePayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


@Service
@Slf4j
public class ExecutionService {
    private final RabbitTemplate rabbitTemplate;

    public ExecutionService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "code-execution-queue")
    public void consumeMessage(MessagePayload payload) {
        try {
            log.info("Received execution request: {}", payload.getRequestId());

            // Perform mock execution
            ExecutionResponse response = performExecution(payload);

            // Send response
            rabbitTemplate.convertAndSend("execution-response-queue", response);

            log.info("Sent execution response for request: {}", payload.getRequestId());
        } catch (Exception e) {
            log.error("Error processing execution request", e);
            // Send error response
            ExecutionResponse errorResponse = createErrorResponse(payload, e);
            rabbitTemplate.convertAndSend("execution-response-queue", errorResponse);
        }
    }

    private ExecutionResponse performExecution(MessagePayload payload) {
        // Simulate processing time
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }


        // Create response with code output
        CodeResponse codeResponse = new CodeResponse(payload.getCode());

        return new ExecutionResponse(
                payload.getRequestId(),
                codeResponse,
                500L,
                "SUCCESS"
        );
    }

    private ExecutionResponse createErrorResponse(MessagePayload payload, Exception e) {
        return new ExecutionResponse(
                payload.getRequestId(),
                new CodeResponse(payload.getCode()),
                0L,
                "ERROR: " + e.getMessage()
        );
    }
}


