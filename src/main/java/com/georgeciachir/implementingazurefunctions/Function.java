package com.georgeciachir.implementingazurefunctions;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.Optional;
import java.util.logging.Logger;

import static com.microsoft.azure.functions.HttpMethod.GET;
import static com.microsoft.azure.functions.annotation.AuthorizationLevel.ANONYMOUS;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Function {

    private static final Logger LOG = Logger.getLogger("Function");

    /**
     * This function listens at endpoint "/api/greeting-function". Two ways to invoke it using "curl" command in bash:
     * curl "{your host}/api/greeting-function?name={your_name_here}"
     */
    @FunctionName("greeting-function")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = GET, authLevel = ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            ExecutionContext context) {
        String name = request.getQueryParameters().get("name");
        context.getLogger().info("Logging with the context logger. Greeting [{" + name + "}]");
        LOG.info("Logging with the manually created logger. Greeting [{" + name + "}]");

        if (name == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("Please pass a name on the query string !").build();
        } else {
            return request.createResponseBuilder(HttpStatus.OK)
                    .body("Hello, " + name + " !").build();
        }
    }
}
