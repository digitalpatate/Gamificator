package ch.heigvd.amt.gamificator.repositories;

import ch.heigvd.amt.gamificator.entities.Application;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ApplicationRepository extends CrudRepository<Application, Long> {

    Optional<Application> findByKey(String key);
}
