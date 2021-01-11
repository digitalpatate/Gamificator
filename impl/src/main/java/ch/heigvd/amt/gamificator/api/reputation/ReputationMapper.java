package ch.heigvd.amt.gamificator.api.reputation;

import ch.heigvd.amt.gamificator.api.badge.BadgeMapper;
import ch.heigvd.amt.gamificator.api.model.BadgeDTO;
import ch.heigvd.amt.gamificator.api.model.PointRewardDTO;
import ch.heigvd.amt.gamificator.api.model.ReputationDTO;
import ch.heigvd.amt.gamificator.entities.BadgeReward;
import ch.heigvd.amt.gamificator.entities.PointsReward;
import ch.heigvd.amt.gamificator.entities.Reputation;
import ch.heigvd.amt.gamificator.entities.Reward;

import java.util.List;

public class ReputationMapper {

    public static ReputationDTO toDTO(Reputation reputation) {
        ReputationDTO reputationDTO = new ReputationDTO();

        for (Reward reward : reputation.getReward()) {
            if (reward instanceof BadgeReward) {
                reputationDTO.addBadgesRewardItem(
                        (BadgeMapper.toDTO(((BadgeReward) reward).getBadge())));
            }


            else if (reward instanceof PointsReward) {
                PointsReward pointsReward = (PointsReward) reward;

                /**
                 * create and set a point reward
                 * No PointRewardService available
                 */
                PointRewardDTO pointRewardDTO = new PointRewardDTO();
                pointRewardDTO.setNbPoint(pointsReward.getPoints());
                pointRewardDTO.setPointScaleDescription(pointsReward.getPointScale().getDescription());
                pointRewardDTO.setPointScaleName(pointsReward.getPointScale().getName());

                reputationDTO.addPointsRewardItem(pointRewardDTO);
            }
        }


        return reputationDTO;
    }
}
