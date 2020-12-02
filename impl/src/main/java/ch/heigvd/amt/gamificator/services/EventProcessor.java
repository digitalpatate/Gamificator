package ch.heigvd.amt.gamificator.services;

import ch.heigvd.amt.gamificator.api.model.ActionDTO;
import ch.heigvd.amt.gamificator.api.model.AwardPointDTO;
import ch.heigvd.amt.gamificator.api.model.ConditionDTO;
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
    private RewardService rewardService;

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
            ConditionDTO conditionDTO = rule.toDTO().getCondition();

            return conditionDTO.getType().equals(event.getType());
        }).collect(Collectors.toList());

        for (Rule rule: rules) {
            ActionDTO actionDTO = rule.toDTO().getThen();
            processAction(actionDTO, event.getUser());
        }
    }

    private void processAction(ActionDTO actionDTO, User user){
        for(String badge : actionDTO.getAwardBadges()){
            Optional<Badge> b = badgeRepository.findByName(badge);
            if(b.isPresent()) {
                rewardService.addBadgeToUser(b.get(), user);
            } else {
                log.severe("badge " + badge + " as not been found!");
            }
        }

        for(AwardPointDTO awardPointDTO : actionDTO.getAwardPoints()){
            Optional<PointScale> p = pointScaleRepository.findByName(awardPointDTO.getPointScaleName());
            if(p.isPresent()) {
                rewardService.addPointsToUser(p.get(), awardPointDTO.getValue(), user);
            } else {
                log.severe("pointscale " + awardPointDTO.getPointScaleName() + " as not been found!");
            }
        }
    }
}
