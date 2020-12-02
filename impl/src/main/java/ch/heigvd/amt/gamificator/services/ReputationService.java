package ch.heigvd.amt.gamificator.services;

import ch.heigvd.amt.gamificator.entities.Reputation;
import ch.heigvd.amt.gamificator.entities.Reward;
import ch.heigvd.amt.gamificator.entities.Rule;
import ch.heigvd.amt.gamificator.entities.User;
import ch.heigvd.amt.gamificator.repositories.ReputationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
@AllArgsConstructor
public class ReputationService {

    private final ReputationRepository reputationRepository;

    public void addRewardToUser(Rule rule, User user){
        Reputation reputation = new Reputation();
        reputation.setRule(rule);
        reputation.setUser(user);
        reputationRepository.save(reputation);
    }
}
