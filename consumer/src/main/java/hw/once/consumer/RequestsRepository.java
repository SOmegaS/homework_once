package hw.once.consumer;

import org.springframework.data.repository.CrudRepository;

public interface RequestsRepository extends CrudRepository<Request, Long> {
    Request findByText(String text);
}
