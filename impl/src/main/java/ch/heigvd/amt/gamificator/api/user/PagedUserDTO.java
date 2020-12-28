package ch.heigvd.amt.gamificator.api.user;

import ch.heigvd.amt.gamificator.api.model.UserDTO;
import ch.heigvd.amt.gamificator.entities.User;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PagedUserDTO {
    private List<UserDTO> users;
    private long total;
    private long nextPage;
    private long numberOfPage;

    public PagedUserDTO(Page<User> userPage){
        this.users = UserMapper.toDTO(userPage.getContent());
        this.total = userPage.getTotalElements();
        this.numberOfPage = userPage.getTotalPages();
    }
}
