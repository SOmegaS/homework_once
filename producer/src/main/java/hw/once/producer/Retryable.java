package hw.once.producer;

import java.io.IOException;

public interface Retryable<T> {
    boolean execute(T args) throws IOException, InterruptedException;
}
