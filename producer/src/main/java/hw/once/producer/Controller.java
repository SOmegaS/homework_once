package hw.once.producer;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class Controller {
    Producer producer;

    @Autowired
    public Controller(Producer producer) {
        this.producer = producer;
    }

    @GetMapping("/createRequests")
    public boolean createRequests(@Nullable Integer count, @Nullable Integer timeout, @Nullable Integer retryCount) {
        return producer.createRequests(count, timeout, retryCount);
    }
}
