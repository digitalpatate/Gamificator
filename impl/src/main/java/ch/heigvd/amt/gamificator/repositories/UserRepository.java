package ch.heigvd.amt.gamificator.repositories;

import ch.heigvd.amt.gamificator.entities.User;
import ch.heigvd.amt.gamificator.entities.UserId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Iterable<User> findAllByApplicationId(long applicationId);
    List<User> findAllByApplicationId(long applicationId, Pageable pageable);
    Optional<User> findByUserId(UserId userId);
}

//b363d24f-a50b-4241-8666-2ecd48086967:ccmCGMHkJU