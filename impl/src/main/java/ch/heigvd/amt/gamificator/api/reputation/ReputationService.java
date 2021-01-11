package ch.heigvd.amt.gamificator.api.reputation;


import ch.heigvd.amt.gamificator.api.model.ReputationDTO;
import ch.heigvd.amt.gamificator.entities.*;
import ch.heigvd.amt.gamificator.exceptions.NotFoundException;
import ch.heigvd.amt.gamificator.repositories.ApplicationRepository;
import ch.heigvd.amt.gamificator.repositories.ReputationRepository;
import ch.heigvd.amt.gamificator.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ReputationService {

    private final ReputationRepository reputationRepository;

    private final UserRepository userRepository;

    private final ApplicationRepository applicationRepository;


    public ReputationDTO getReputationByUserId(UUID uuid) throws NotFoundException {
        UserId userId = new UserId();
        userId.setUUID(uuid.toString());
        Reputation reputation = reputationRepository.findByUser(userRepository.findByUserId(userId).get())
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
