package ch.heigvd.amt.gamificator.api.user;

import ch.heigvd.amt.gamificator.api.ApiUtil;
import ch.heigvd.amt.gamificator.api.RulesApi;
import ch.heigvd.amt.gamificator.api.UsersApi;
import ch.heigvd.amt.gamificator.api.model.RuleCreateCommand;
import ch.heigvd.amt.gamificator.api.model.RuleDTO;
import ch.heigvd.amt.gamificator.api.model.RuleUpdateCommand;
import ch.heigvd.amt.gamificator.api.model.UserDTO;
import ch.heigvd.amt.gamificator.api.rule.RuleService;
import ch.heigvd.amt.gamificator.exceptions.NotFoundException;
import ch.heigvd.amt.gamificator.exceptions.RelatedObjectNotFound;
import ch.heigvd.amt.gamificator.services.SecurityContextService;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@Log
public class UserController implements UsersApi {

    @Autowired
    private UserService userService;


    @Autowired
    private SecurityContextService securityContextService;

    @Override
    public ResponseEntity<UserDTO> getUserByUUID(UUID uuid) {
        UserDTO userDTO = null;

        try {
            userDTO = userService.findUserByUUID(uuid);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<UserDTO> getAllUser(Integer perPage, Integer page) {
        page = (page != null) ? page : 0;
        perPage = (perPage != null) ? perPage : 10;
        Pageable pageable = PageRequest.of(page,perPage);
        long id = securityContextService.getApplicationIdFromAuthentifiedApp();

        PagedUserDTO usersDTO = userService.findAllByApplicationPageable(id,pageable);
        usersDTO.setNextPage(page+1);
        return new ResponseEntity(usersDTO,HttpStatus.OK);
    }
}
