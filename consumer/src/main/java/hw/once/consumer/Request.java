package hw.once.consumer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.Instant;

@Entity
public class Request {
    @Id
    @GeneratedValue
    Long id;

    String text;

    Instant time;

    public Request(String text) {
        this.text = text;
        time = Instant.now();
    }

    public Request() {
        text = null;  // TODO
        time = Instant.now();
    }
}
