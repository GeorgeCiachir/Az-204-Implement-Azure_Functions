package com.georgeciachir.implementingazurefunctions;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.QueueTrigger;

/**
 * Azure Functions with Azure Storage Queue trigger.
 */
public class QueueTriggeredFunction {
    /**
     * This function will be invoked when a new message is received at the specified path. The message contents are provided as input to this function.
     */
    @FunctionName("queue-triggered-function")
    public void queueTrigger(
            @QueueTrigger(name = "message", queueName = "queue-items", connection = "AzureWebJobsStorage") String message,
            final ExecutionContext context) {
        context.getLogger().info("Java Queue trigger function processed a message: " + message);
    }
}
