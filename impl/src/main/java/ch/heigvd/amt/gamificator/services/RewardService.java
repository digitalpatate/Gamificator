package ch.heigvd.amt.gamificator.services;

import ch.heigvd.amt.gamificator.api.model.AwardPointDTO;
import ch.heigvd.amt.gamificator.api.model.BadgeDTO;
import ch.heigvd.amt.gamificator.entities.*;
import ch.heigvd.amt.gamificator.repositories.*;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log
@AllArgsConstructor
public class RewardService {

    private final BadgeRewardRepository badgeRewardRepository;
    private final PointsRewardRepository pointsRewardRepository;

    public void addBadgeToRule(Badge badge, Rule rule){
        BadgeReward badgeAward = new BadgeReward();
        badgeAward.setBadge(badge);
        badgeAward.setRule(rule);
        badgeRewardRepository.save(badgeAward);
    }

    public void addPointsToReward(PointScale pointScale, Rule rule, int points){
        PointsReward pointsAward = new PointsReward();
        pointsAward.setPointScale(pointScale);
        pointsAward.setPoints(points);
        pointsAward.setRule(rule);
        pointsRewardRepository.save(pointsAward);
    }

    public List<String> getAllBadgesWithRuleId(Long ruleId) {
        List<String> badgesName = new ArrayList<>();
        List<BadgeReward> badges = badgeRewardRepository.findAllByRuleId(ruleId);
        for (BadgeReward badgeReward : badges) {
            badgesName.add(badgeReward.getBadge().getName());
        }

        return badgesName;
    }

    public List<AwardPointDTO> getAllPointsWithRuleId(Long ruleId) {
        List<AwardPointDTO> awardPoints = new ArrayList<>();
        List<PointsReward> points = pointsRewardRepository.findAllByRuleId(ruleId);
        for (PointsReward pointsReward : points) {
            AwardPointDTO awardPointDTO = new AwardPointDTO();
            awardPointDTO.setPointScaleName(pointsReward.getPointScale().getName());
            awardPointDTO.setValue(pointsReward.getPoints());
            awardPoints.add(awardPointDTO);
        }

        return awardPoints;
    }
}
