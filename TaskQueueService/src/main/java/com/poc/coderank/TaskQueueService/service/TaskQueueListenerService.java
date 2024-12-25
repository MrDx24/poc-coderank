package com.poc.coderank.TaskQueueService.service;


import com.poc.coderank.TaskQueueService.model.ExecutionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class TaskQueueListenerService {
    private final ResponseHolder responseHolder;

    public TaskQueueListenerService(ResponseHolder responseHolder) {
        this.responseHolder = responseHolder;
    }

    @RabbitListener(queues = "execution-response-queue")
    public void receiveExecutionResponse(ExecutionResponse response) {
        try {
            log.info("*************************************************************************");
            log.info("Received execution response for request: {}", response.getRequestId());

            // Complete the future with the response
            CompletableFuture<ExecutionResponse> future = responseHolder.getFuture(response.getRequestId());
            if (future != null) {
                future.complete(response);
            } else {
                log.warn("No waiting request found for response ID: {}", response.getRequestId());
            }

        } catch (Exception e) {
            log.error("Error processing execution response", e);
            CompletableFuture<ExecutionResponse> future = responseHolder.getFuture(response.getRequestId());
            if (future != null) {
                future.completeExceptionally(e);
            }
        }
    }
}
