package ch.heigvd.amt.gamificator.api.user;

import ch.heigvd.amt.gamificator.api.model.UserDTO;
import ch.heigvd.amt.gamificator.api.model.UserScoreDTO;
import ch.heigvd.amt.gamificator.entities.User;
import ch.heigvd.amt.gamificator.entities.UserId;
import org.springframework.data.domain.Page;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUuid(UUID.fromString(user.getUserId().getUUID()));
        userDTO.setApplicationId(user.getApplication().getId());
        return userDTO;
    }

    public static User toEntity(UserDTO userDTO) {
        User user = new User();
        UserId userId = new UserId();
        userId.setUUID(userDTO.getUuid().toString());
        userId.setApplicationId(userDTO.getApplicationId());
        user.setUserId(userId);
        return user;
    }

    public static List<UserDTO> toDTO(List<User> users) {
        List<UserDTO> userDTOs = new LinkedList<>();

        users.forEach(user -> {
            userDTOs.add(UserMapper.toDTO(user));
        });

        return userDTOs;
    }
}
