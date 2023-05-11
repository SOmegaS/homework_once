package hw.once.consumer;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@Slf4j
public class Controller {
    RequestsRepository requestsRepository;

    @Autowired
    public Controller(RequestsRepository requestsRepository) {
        this.requestsRepository = requestsRepository;
    }

    @GetMapping("/test")
    public boolean test() {
        log.info("Task received");
        return true;
    }

    @GetMapping("/print")
    public boolean print(@Nullable String text) {
        Request inRepoRequest = requestsRepository.findByText(text);
        if (inRepoRequest != null && (Instant.now().toEpochMilli() - inRepoRequest.time.toEpochMilli()) <= 10_000) {
            return false;
        }
        if (inRepoRequest != null) {
            requestsRepository.delete(inRepoRequest);
        }
        log.info(text == null ? "\n" : text);
        requestsRepository.save(new Request(text));
        return true;
    }
}
