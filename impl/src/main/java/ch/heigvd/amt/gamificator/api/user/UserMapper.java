package ch.heigvd.amt.gamificator.api.user;

import ch.heigvd.amt.gamificator.api.model.UserDTO;
import ch.heigvd.amt.gamificator.entities.User;

import java.util.UUID;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUuid(UUID.fromString(user.getUUID()));
        return userDTO;
    }

    public static User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setUUID(userDTO.getUuid().toString());
        return user;
    }
}
