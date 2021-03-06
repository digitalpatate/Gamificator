package ch.heigvd.amt.gamificator.api.leaderboard;

import ch.heigvd.amt.gamificator.api.model.LeaderBoardDTO;
import ch.heigvd.amt.gamificator.api.model.UserDTO;
import ch.heigvd.amt.gamificator.api.model.UserScoreDTO;
import ch.heigvd.amt.gamificator.api.user.PagedUserDTO;
import ch.heigvd.amt.gamificator.api.user.UserMapper;
import ch.heigvd.amt.gamificator.api.user.UserService;
import ch.heigvd.amt.gamificator.entities.PointScale;
import ch.heigvd.amt.gamificator.entities.PointsReward;
import ch.heigvd.amt.gamificator.entities.Reputation;
import ch.heigvd.amt.gamificator.exceptions.NotFoundException;
import ch.heigvd.amt.gamificator.repositories.PointScaleRepository;
import ch.heigvd.amt.gamificator.repositories.ReputationRepository;
import ch.heigvd.amt.gamificator.services.SecurityContextService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.*;
import java.util.List;

@Service
@AllArgsConstructor
public class LeaderboardService {

    private final ReputationRepository reputationRepository;

    private final PointScaleRepository pointScaleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityContextService securityContextService;

    public LeaderBoardDTO getLeaderboardOnPointScale(String pointScaleName) {
        List<UserDTO> userDTOs = userService.findAllUserOfApplication(
                securityContextService.getApplicationIdFromAuthentifiedApp()
        );

        List<UserScoreDTO> userScoreDTOs = new LinkedList<>();

        userDTOs.forEach(userDTO -> {
            reputationRepository.findByUser(UserMapper.toEntity(userDTO)).ifPresent(reputation -> {
                int userPoints = reputation.getReward()
                        .stream()
                        .filter(reward -> reward instanceof PointsReward)
                        .map(reward -> (PointsReward) reward)
                        .filter(pointsReward -> pointsReward.getPointScale().getName().equals(pointScaleName))
                        .reduce(0, (subtotal, pointsReward) -> subtotal + pointsReward.getPoints(), Integer::sum);

                UserScoreDTO userScoreDTO = new UserScoreDTO();
                userScoreDTO.setUser(userDTO);
                userScoreDTO.setScore(userPoints);
                userScoreDTOs.add(userScoreDTO);
            });
        });

        userScoreDTOs.sort(Comparator.comparing(UserScoreDTO::getScore).reversed().thenComparing(u -> u.getUser().getUuid()));

        LeaderBoardDTO leaderBoardDTO = new LeaderBoardDTO();
        leaderBoardDTO.setLeaderboard(userScoreDTOs);

        return leaderBoardDTO;
    }

    public LeaderBoardDTO getLeaderboardOnPointScale(String pointScaleName, Pageable pageable) throws NotFoundException {
        long applicationId = securityContextService.getApplicationIdFromAuthentifiedApp();

        Optional<PointScale> oPointScale = pointScaleRepository.findByNameAndApplicationId(pointScaleName,applicationId);

        if(oPointScale.isEmpty()){
            throw new NotFoundException(String.format("The pointScale by name %s cannot be found",pointScaleName));
        }
        PagedUserDTO userDTOs = userService.findAllByApplicationPageable(applicationId,pageable);

        List<UserScoreDTO> userScoreDTOs = new LinkedList<>();

        userDTOs.getUsers().forEach(userDTO -> {
            reputationRepository.findByUser(UserMapper.toEntity(userDTO)).ifPresent(reputation -> {
                int userPoints = reputation.getReward()
                        .stream()
                        .filter(reward -> reward instanceof PointsReward)
                        .map(reward -> (PointsReward) reward)
                        .filter(pointsReward -> pointsReward.getPointScale().getName().equals(pointScaleName))
                        .reduce(0, (subtotal, pointsReward) -> subtotal + pointsReward.getPoints(), Integer::sum);

                UserScoreDTO userScoreDTO = new UserScoreDTO();
                userScoreDTO.setUser(userDTO);
                userScoreDTO.setScore(userPoints);
                userScoreDTOs.add(userScoreDTO);
            });
        });

        userScoreDTOs.sort(Comparator.comparing(UserScoreDTO::getScore).reversed().thenComparing(u -> u.getUser().getUuid()));

        LeaderBoardDTO leaderBoardDTO = new LeaderBoardDTO();
        leaderBoardDTO.setLeaderboard(userScoreDTOs);
        leaderBoardDTO.setNumberOfPage(userDTOs.getNumberOfPage());
        leaderBoardDTO.setTotal(userDTOs.getTotal());
        return leaderBoardDTO;
    }
}
