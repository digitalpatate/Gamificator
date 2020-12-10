package ch.heigvd.amt.gamificator.api.user;

import ch.heigvd.amt.gamificator.api.model.UserDTO;
import ch.heigvd.amt.gamificator.entities.User;
import ch.heigvd.amt.gamificator.entities.UserId;

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
}
