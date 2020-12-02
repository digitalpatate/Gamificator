package ch.heigvd.amt.gamificator.services;

import ch.heigvd.amt.gamificator.api.model.ActionDTO;
import ch.heigvd.amt.gamificator.api.model.AwardPointDTO;
import ch.heigvd.amt.gamificator.api.model.ConditionDTO;
import ch.heigvd.amt.gamificator.api.rule.RuleMapper;
import ch.heigvd.amt.gamificator.entities.*;
import ch.heigvd.amt.gamificator.repositories.BadgeRepository;
import ch.heigvd.amt.gamificator.repositories.PointScaleRepository;
import ch.heigvd.amt.gamificator.repositories.RuleRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log
public class EventProcessor {

    @Autowired
    private ReputationService reputationService;

    private final RuleRepository ruleRepository;
    private final BadgeRepository badgeRepository;
    private final  PointScaleRepository pointScaleRepository;

    public EventProcessor(RuleRepository ruleRepository, BadgeRepository badgeRepository, PointScaleRepository pointScaleRepository) {
        this.ruleRepository = ruleRepository;
        this.badgeRepository = badgeRepository;
        this.pointScaleRepository = pointScaleRepository;
    }

    public void process(Event event) {
        List<Rule> rules = (List<Rule>) ruleRepository.findAll();

        rules = rules.stream().filter(rule ->  {
            ConditionDTO conditionDTO = RuleMapper.toDTO(rule).getCondition();

            return conditionDTO.getType().equals(event.getType());
        }).collect(Collectors.toList());

        for (Rule rule: rules) {
            reputationService.addRewardToUser(rule, event.getUser());
        }
    }
}
