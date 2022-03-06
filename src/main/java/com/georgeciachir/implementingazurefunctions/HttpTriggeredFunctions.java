package com.georgeciachir.implementingazurefunctions;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.Optional;

import static com.microsoft.azure.functions.HttpMethod.GET;
import static com.microsoft.azure.functions.annotation.AuthorizationLevel.ANONYMOUS;

public class HttpTriggeredFunctions {

    /**
     * curl "{your host}/api/greeting-function-one?name={your_name_here}"
     */
    @FunctionName("greeting-function-one")
    public HttpResponseMessage firstHttpTrigger(
            @HttpTrigger(name = "requestTrigger1", methods = GET, authLevel = ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            ExecutionContext context) {
        return constructGreetingMessage(request, context);
    }

    /**
     * curl "{your host}/api/greeting-function-one?name={your_name_here}"
     */
    @FunctionName("greeting-function-two")
    public HttpResponseMessage secondHttpTrigger(
            @HttpTrigger(name = "requestTrigger1", methods = GET, authLevel = ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            ExecutionContext context) {
        return constructGreetingMessage(request, context);
    }

    private HttpResponseMessage constructGreetingMessage(HttpRequestMessage<Optional<String>> request, ExecutionContext context) {
        String name = request.getQueryParameters().get("name");
        context.getLogger().info("Greeting [" + name + "]");

        if (name == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("Please pass a name on the query string !").build();
        } else {
            return request.createResponseBuilder(HttpStatus.OK)
                    .body("Hello, " + name + " !").build();
        }
    }
}
