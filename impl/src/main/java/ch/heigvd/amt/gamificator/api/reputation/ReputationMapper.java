package ch.heigvd.amt.gamificator.api.reputation;

import ch.heigvd.amt.gamificator.api.badge.BadgeMapper;
import ch.heigvd.amt.gamificator.api.model.BadgeDTO;
import ch.heigvd.amt.gamificator.api.model.PointRewardDTO;
import ch.heigvd.amt.gamificator.api.model.ReputationDTO;
import ch.heigvd.amt.gamificator.entities.*;

import java.util.ArrayList;
import java.util.List;

public class ReputationMapper {

    public static ReputationDTO toDTO(Reputation reputation) {
        ReputationDTO reputationDTO = new ReputationDTO();

        for (Reward reward : reputation.getReward()) {
            if (reward instanceof BadgeReward) {

                BadgeReward badgeReward = (BadgeReward) reward;
                reputationDTO.addBadgesRewardItem(
                        (BadgeMapper.toDTO(badgeReward.getBadge())));
//
//                if(!check_if_present(reputationDTO.getBadgesReward(), badgeReward)){
//                    reputationDTO.addBadgesRewardItem(
//                            (BadgeMapper.toDTO(badgeReward.getBadge())));
//                } else {
//                    for(BadgeDTO badgeDTO : reputationDTO.getBadgesReward()){
//                        if(badgeDTO.getName().equals(badgeReward.getBadge().getName())){
//                            badgeDTO.setOccurence(badgeDTO.getOccurence() + 1);
//                        }
//                    }
//                }

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

        reputationDTO.setPointsReward(sum_points(reputationDTO.getPointsReward()));
        return reputationDTO;
    }


    private static List<PointRewardDTO> sum_points(List<PointRewardDTO> list){
        List<PointRewardDTO> sum = new ArrayList<>();

        for(PointRewardDTO pointRewardDTO: list){
            if(!check_if_present(sum, pointRewardDTO)){
                sum.add(empty_point(pointRewardDTO));
            }
        }

        for(PointRewardDTO pointRewardDTO: list){
            update_sum(sum, pointRewardDTO);
        }
        return sum;
    }

    private static boolean check_if_present(List<PointRewardDTO> list, PointRewardDTO pointRewardDTO){
        for(PointRewardDTO pt: list){
            if(pt.getPointScaleName().equals(pointRewardDTO.getPointScaleName())){
                return true;
            }
        }
        return false;
    }

    private static boolean check_if_present(List<BadgeDTO> list, BadgeReward badgeReward){
        for(BadgeDTO badgeDTO: list){
            if(badgeDTO.getName().equals(badgeReward.getBadge().getName())){
                return true;
            }
        }
        return false;
    }

    private static PointRewardDTO empty_point(PointRewardDTO point){
        PointRewardDTO new_pt = new PointRewardDTO();
        new_pt.setNbPoint(0);
        new_pt.setPointScaleName(point.getPointScaleName());
        new_pt.setPointScaleDescription(point.getPointScaleDescription());
        return new_pt;
    }

    private static void update_sum(List<PointRewardDTO> list, PointRewardDTO pointRewardDTO){
        for(PointRewardDTO pt_sum : list){
            if(pt_sum.getPointScaleName().equals(pointRewardDTO.getPointScaleName())){
                pt_sum.setNbPoint(pt_sum.getNbPoint() + pointRewardDTO.getNbPoint());
            }
        }
    }
}
