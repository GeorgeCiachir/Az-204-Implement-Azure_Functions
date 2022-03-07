package com.georgeciachir.implementingazurefunctions;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.TimerTrigger;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.time.LocalDateTime;

public class TimerTriggeredFunction {

    @FunctionName("timer-function")
    public void timerTrigger(
            @TimerTrigger(name = "timerInfo", schedule = "*/10 * * * * *") String timerInfo,
            ExecutionContext context) throws Exception {
        context.getLogger().info("Sending request executed at: " + LocalDateTime.now());
        context.getLogger().info("TimerInfo: " + timerInfo);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://greeting-function-test-env.azurewebsites.net/api/http-greeting-function-one?name=TimerTriggeredFunction") //Azure
//                .url("http://localhost:7071/api/http-greeting-function-one?name=TimerTriggeredFunction") //local
                .build();

        Call call = client.newCall(request);
        call.execute();
    }
}
