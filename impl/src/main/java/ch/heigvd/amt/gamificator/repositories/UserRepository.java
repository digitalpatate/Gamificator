package ch.heigvd.amt.gamificator.repositories;

import ch.heigvd.amt.gamificator.entities.User;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface UserRepository extends CrudRepository<User, Long> {

}
