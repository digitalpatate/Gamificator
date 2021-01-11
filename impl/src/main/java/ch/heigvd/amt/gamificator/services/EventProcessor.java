package ch.heigvd.amt.gamificator.services;

import ch.heigvd.amt.gamificator.api.model.ConditionDTO;
import ch.heigvd.amt.gamificator.api.reputation.ReputationService;
import ch.heigvd.amt.gamificator.api.rule.RuleMapper;
import ch.heigvd.amt.gamificator.entities.*;
import ch.heigvd.amt.gamificator.exceptions.NotFoundException;
import ch.heigvd.amt.gamificator.repositories.RewardRepository;
import ch.heigvd.amt.gamificator.repositories.RuleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log
@AllArgsConstructor
public class EventProcessor {

    @Autowired
    private ReputationService reputationService;

    private final RuleRepository ruleRepository;
    private final RewardRepository rewardRepository;

    public void process(Event event, long applicationId) throws NotFoundException {
        List<Rule> rules = ruleRepository.findAllByApplicationId(applicationId);

        rules = rules.stream().filter(rule ->  {
            ConditionDTO conditionDTO = RuleMapper.toDTO(rule).getCondition();

            return conditionDTO.getType().equals(event.getType());
        }).collect(Collectors.toList());

        if(rules.size() < 0){
            throw new NotFoundException("No rules have been found with the given name");
        }

        for (Rule rule: rules) {
            List<Reward> rewards = rewardRepository.findByRuleId(rule.getId());
            reputationService.addRewardToUser(rewards, event.getUser());
        }
    }
}
