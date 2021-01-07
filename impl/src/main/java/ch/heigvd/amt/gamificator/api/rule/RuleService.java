package ch.heigvd.amt.gamificator.api.rule;

import ch.heigvd.amt.gamificator.api.model.*;
import ch.heigvd.amt.gamificator.entities.*;
import ch.heigvd.amt.gamificator.exceptions.NotAuthorizedException;
import ch.heigvd.amt.gamificator.exceptions.NotFoundException;
import ch.heigvd.amt.gamificator.exceptions.RelatedObjectNotFound;
import ch.heigvd.amt.gamificator.repositories.*;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.LinkedList;
import java.util.List;


@Service
@AllArgsConstructor
public class RuleService {

    private final RuleRepository ruleRepository;
    private final ApplicationRepository applicationRepository;
    private final PointsRewardRepository pointsRewardRepository;
    private final BadgeRewardRepository badgeRewardRepository;
    private final BadgeRepository badgeRepository;
    private final PointScaleRepository pointScaleRepository;

    @Synchronized
    public RuleDTO create(RuleCreateCommand ruleCreateCommand, Long applicationId) throws RelatedObjectNotFound {
        Rule rule = RuleMapper.toEntity(ruleCreateCommand);
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> new RelatedObjectNotFound("Your application isn't registered"));

        rule.setApplication(application);
        rule = ruleRepository.save(rule);

        List<Reward> rewards = new LinkedList<>();
        List<String> awardBadges = ruleCreateCommand.getThen().getAwardBadges();
        for(String badgeName : awardBadges){
            Badge badge = badgeRepository.findByNameAndApplicationId(badgeName, application.getId()).orElseThrow(() -> new RelatedObjectNotFound("No badge found with the given name"));
            BadgeReward badgeReward = new BadgeReward();
            badgeReward.setBadge(badge);
            badgeReward.setRule(rule);
            rewards.add(badgeReward);
            badgeRewardRepository.save(badgeReward);
        }

        List<AwardPointDTO> awardPointDTOS = ruleCreateCommand.getThen().getAwardPoints();
        for(AwardPointDTO awardPointDTO : awardPointDTOS){
            PointScale pointScale = pointScaleRepository.findByNameAndApplicationId(awardPointDTO.getPointScaleName(), application.getId()).orElseThrow(() -> new RelatedObjectNotFound("No PointScale found with the given name"));
            PointsReward pointsReward = new PointsReward();
            pointsReward.setPointScale(pointScale);
            pointsReward.setPoints(awardPointDTO.getValue());
            pointsReward.setRule(rule);
            rewards.add(pointsReward);
            pointsRewardRepository.save(pointsReward);
        }

        ActionDTO actionDTO = new ActionDTO();
        actionDTO.setAwardPoints(awardPointDTOS);
        actionDTO.setAwardBadges(awardBadges);

        RuleDTO ruleDTO = RuleMapper.toDTO(rule);
        ruleDTO.setThen(actionDTO);
        return ruleDTO;
    }


    public void delete(Long id, Long applicationId) throws NotFoundException, NotAuthorizedException {
        List<Rule> rules = ruleRepository.findAllByApplicationId(applicationId);
        if(rules.size() > 0) {
            for (Rule rule : rules) {
                if(rule.getId() == id){
                    if (rule.getApplication().getId() == applicationId) {
                        ruleRepository.deleteById(id);
                        return;
                    } else {
                        throw new NotAuthorizedException("This application isn't the owner of the rule");
                    }
                }
            }
        }
        throw new NotFoundException("This rule does not exist");
    }

    public List<RuleDTO> getAllRules(Long applicationId) {
        List<RuleDTO> rules = new LinkedList<>();
        for (Rule rule : ruleRepository.findAllByApplicationId(applicationId)){
            RuleDTO ruleDTO = RuleMapper.toDTO(rule);

            List<PointsReward> pointsRewards = pointsRewardRepository.findAllByRuleId(rule.getId());
            List<AwardPointDTO> awardPointDTOS = new LinkedList<>();

            pointsRewards.forEach(pointsReward -> {
                AwardPointDTO awardPointDTO = new AwardPointDTO(); // FIXME: should have a mapper
                awardPointDTO.setPointScaleName(pointsReward.getPointScale().getName());
                awardPointDTO.setValue(pointsReward.getPoints());
                awardPointDTOS.add(awardPointDTO);
            });

            List<BadgeReward> badgeRewards = badgeRewardRepository.findAllByRuleId(rule.getId());
            List<String> badges = new LinkedList<>();
            badgeRewards.forEach(br -> {
                badges.add(br.getBadge().getName());
            });

            ActionDTO actionDTO = new ActionDTO();
            actionDTO.setAwardPoints(awardPointDTOS);
            actionDTO.setAwardBadges(badges);
            ruleDTO.setThen(actionDTO);

            rules.add(ruleDTO);
        }

        return rules;

    }

    public RuleDTO update(Long id,RuleUpdateCommand command) throws NotFoundException {
        if(!ruleExists(id)){
            throw new NotFoundException("This rule does not exist");
        }
        Rule rule = RuleMapper.toEntity(command,id);

        rule = ruleRepository.save(rule);

        return RuleMapper.toDTO(rule);
    }

    private boolean ruleExists(long id){
        return ruleRepository.findById(id).isPresent();
    }
}
