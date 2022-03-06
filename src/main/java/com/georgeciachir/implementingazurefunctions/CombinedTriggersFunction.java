package com.georgeciachir.implementingazurefunctions;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.microsoft.azure.functions.annotation.QueueTrigger;

import java.util.Optional;

import static com.microsoft.azure.functions.HttpMethod.GET;
import static com.microsoft.azure.functions.annotation.AuthorizationLevel.ANONYMOUS;

public class CombinedTriggersFunction {

    /**
     * curl "{your host}/api/combined-triggers-function?name={your_name_here}"
     */
    @FunctionName("combined-triggers-function")
    public HttpResponseMessage secondHttpTrigger(
            @HttpTrigger(
                    name = "request",
                    methods = GET,
                    authLevel = ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            @QueueTrigger(name = "message", queueName = "queue-items", connection = "AzureWebJobsStorage") String message,
            ExecutionContext context) {
        if (message != null) {
            return constructResponseBasedOnMessage(request, message, context);
        }
        return constructGreetingMessage(request, context);
    }

    private HttpResponseMessage constructResponseBasedOnMessage(HttpRequestMessage<Optional<String>> request, String message, ExecutionContext context) {
        context.getLogger().info("Java combined triggers function processed a message: " + message);
        return request.createResponseBuilder(HttpStatus.OK).build();
    }

    private HttpResponseMessage constructGreetingMessage(HttpRequestMessage<Optional<String>> request, ExecutionContext context) {
        String name = request.getQueryParameters().get("name");
        context.getLogger().info("Greeting [" + name + "]");

        if (name == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("Please pass a name on the query string !").build();
        } else {
            return request.createResponseBuilder(HttpStatus.OK)
                    .body("Hello from the combined triggers function, " + name + " !").build();
        }
    }
}
