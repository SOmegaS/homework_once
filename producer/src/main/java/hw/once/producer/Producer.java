package hw.once.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Configuration
@Slf4j
public class Producer {
    HttpClient client;

    @Autowired
    public Producer() {
        this.client = HttpClient.newHttpClient();
    }

    public void sendRequest(String URL, Integer timeout) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .timeout(Duration.ofMillis(timeout))
                .uri(URI.create(URL))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        log.info(response.toString());
    }

    private Integer checkCorrect(Integer number, Integer defaultNumber) {
        if (number == null) {
            return defaultNumber;
        }
        if (number < 0) {
            return defaultNumber;
        }
        return number;
    }

    public <T> boolean doWithRetry(Retryable<T> predicate, T arg, int retriesCount) {
        boolean isOk = false;
        for (int i = 0; !isOk && i < retriesCount; ++i) {
            try {
                isOk = predicate.execute(arg);
            } catch (Exception ignored) {
            }
        }
        return isOk;
    }

    public boolean createRequests(Integer count, Integer timeout, Integer retryCount) {
        count = checkCorrect(count, 1);
        timeout = checkCorrect(timeout, 1000);
        retryCount = checkCorrect(retryCount, 3);
        boolean isOk = true;
        for (int i = 0; i < count; ++i) {
            Integer finalTimeout = timeout;
            int finalI = i;
            isOk = doWithRetry((ignore) -> {
                sendRequest("http://localhost:8080/print?text=" + finalI, finalTimeout);
                return true;
            }, null, retryCount) && isOk;
        }
        return isOk;
    }
}
