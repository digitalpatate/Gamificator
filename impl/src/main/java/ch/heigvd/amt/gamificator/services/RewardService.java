package ch.heigvd.amt.gamificator.services;

import ch.heigvd.amt.gamificator.entities.*;
import ch.heigvd.amt.gamificator.repositories.*;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
@AllArgsConstructor
public class RewardService {

    private final BadgeRewardRepository badgeRewardRepository;
    private final PointsRewardRepository pointsRewardRepository;

    public void addBadgeToUser(Badge badge, User user){
        BadgeAward badgeAward = new BadgeAward();
        badgeAward.setBadge(badge);
        badgeAward.setUser(user);
        badgeRewardRepository.save(badgeAward);
    }

    public void addPointsToUser(PointScale pointScale, int points, User user){
        PointsAward pointsAward = new PointsAward();
        pointsAward.setPointScale(pointScale);
        pointsAward.setPoints(points);
        pointsAward.setUser(user);
        pointsRewardRepository.save(pointsAward);
    }
}
