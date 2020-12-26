package ch.heigvd.amt.gamificator.api.user;

import ch.heigvd.amt.gamificator.api.model.UserDTO;
import ch.heigvd.amt.gamificator.entities.User;
import ch.heigvd.amt.gamificator.entities.UserId;
import ch.heigvd.amt.gamificator.exceptions.NotFoundException;
import ch.heigvd.amt.gamificator.repositories.UserRepository;
import ch.heigvd.amt.gamificator.services.SecurityContextService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private SecurityContextService securityContextService;

    private final UserRepository userRepository;

    public UserDTO findUserByUUID(UUID uuid) throws NotFoundException {
        long applicationId = securityContextService.getApplicationIdFromAuthentifiedApp();

        UserId userId = new UserId();
        userId.setApplicationId(applicationId);
        userId.setUUID(uuid.toString());

        User user = userRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException("User not found!"));

        return UserMapper.toDTO(user);
    }

    public List<UserDTO> findAllUserOfApplication(long applicationId) {
        Iterable<User> users = userRepository.findAllByApplicationId(applicationId);
        List<UserDTO> userDTOs = new LinkedList<>();

        users.forEach(user -> {
            userDTOs.add(UserMapper.toDTO(user));
        });

        return userDTOs;
    }
    public List<UserDTO> findAllByApplicationPageable(long applicationId, Pageable pageable) {
        Page<User> users = userRepository.findAllByApplicationId(applicationId,pageable);
        List<UserDTO> userDTOs = new LinkedList<>();

        users.forEach(user -> {
            userDTOs.add(UserMapper.toDTO(user));
        });

        return userDTOs;
    }


}
