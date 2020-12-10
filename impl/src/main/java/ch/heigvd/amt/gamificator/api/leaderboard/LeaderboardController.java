package ch.heigvd.amt.gamificator.api.leaderboard;

import ch.heigvd.amt.gamificator.api.LeaderboardApi;
import ch.heigvd.amt.gamificator.api.model.LeaderBoardDTO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LeaderboardController implements LeaderboardApi {

    @Autowired
    LeaderboardService leaderboardService;

    public ResponseEntity<LeaderBoardDTO> getLeaderboard(@ApiParam(value = "",required=true) @PathVariable("pointScaleName") String pointScaleName) {
        LeaderBoardDTO leaderBoardDTO = leaderboardService.getLeaderboardOnPointScale(pointScaleName);

        return new ResponseEntity<>(leaderBoardDTO, HttpStatus.OK);
    }
}
