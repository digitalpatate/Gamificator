package ch.heigvd.amt.gamificator.api.reputation;


import ch.heigvd.amt.gamificator.api.model.ReputationDTO;
import ch.heigvd.amt.gamificator.api.model.UserDTO;
import ch.heigvd.amt.gamificator.api.user.UserService;
import ch.heigvd.amt.gamificator.entities.*;
import ch.heigvd.amt.gamificator.exceptions.NotFoundException;
import ch.heigvd.amt.gamificator.repositories.ApplicationRepository;
import ch.heigvd.amt.gamificator.repositories.ReputationRepository;
import ch.heigvd.amt.gamificator.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static ch.heigvd.amt.gamificator.api.user.UserMapper.toEntity;

@Service
@AllArgsConstructor
public class ReputationService {

    private final ReputationRepository reputationRepository;

    private final UserRepository userRepository;

    private final ApplicationRepository applicationRepository;


    public ReputationDTO getReputationByUserId(UUID uuid, long appID) throws NotFoundException {
        UserDTO userDTO = new UserDTO();
        userDTO.setUuid(uuid);
        userDTO.setApplicationId(appID);

        Reputation reputation = reputationRepository.findByUser(toEntity(userDTO))
                .orElseThrow(() -> new NotFoundException("Not found"));

        return ReputationMapper.toDTO(reputation);
    }

    public void addRewardToUser(List<Reward> rewards, User user){
        Reputation reputation = reputationRepository.findByUser(user).orElse(new Reputation());
        reputation.addAll(rewards);
        reputation.setUser(user);
        reputationRepository.save(reputation);
    }
}
