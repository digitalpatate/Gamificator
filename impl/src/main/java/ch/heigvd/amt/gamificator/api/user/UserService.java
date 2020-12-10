package ch.heigvd.amt.gamificator.api.user;

import ch.heigvd.amt.gamificator.api.model.UserDTO;
import ch.heigvd.amt.gamificator.entities.User;
import ch.heigvd.amt.gamificator.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserDTO> findAllUserOfApplication(long applicationId) {
        Iterable<User> users = userRepository.findAllByApplicationId(applicationId);
        List<UserDTO> userDTOs = new LinkedList<>();

        users.forEach(user -> {
            userDTOs.add(UserMapper.toDTO(user));
        });

        return userDTOs;
    }

}
