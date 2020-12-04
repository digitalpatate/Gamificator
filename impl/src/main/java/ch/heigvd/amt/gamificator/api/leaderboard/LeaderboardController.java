package ch.heigvd.amt.gamificator.api.leaderboard;

import ch.heigvd.amt.gamificator.api.LeaderboardApi;
import ch.heigvd.amt.gamificator.api.model.LeaderBoardDTO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public class LeaderboardController implements LeaderboardApi {

    @Autowired
    LeaderboardService leaderboardService;

    public ResponseEntity<LeaderBoardDTO> getLeaderboard(@ApiParam(value = "",required=true) @PathVariable("pointScaleId") Long pointScaleId) {
        LeaderBoardDTO leaderBoardDTO = leaderboardService.getLeaderboardOnPointScale(pointScaleId);

        return new ResponseEntity<>(leaderBoardDTO, HttpStatus.OK);
    }
}
