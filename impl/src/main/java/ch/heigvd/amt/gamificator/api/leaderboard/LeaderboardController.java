package ch.heigvd.amt.gamificator.api.leaderboard;

import ch.heigvd.amt.gamificator.api.LeaderboardApi;
import ch.heigvd.amt.gamificator.api.model.LeaderBoardDTO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class LeaderboardController implements LeaderboardApi {

    @Autowired
    LeaderboardService leaderboardService;


    @Override
    public ResponseEntity<LeaderBoardDTO> getLeaderboard(String pointScaleName, @Valid Integer perPage, @Valid Integer page) {

        page = (page != null) ? page : 0;
        perPage = (perPage != null) ? perPage : 10;
        Pageable pageable = PageRequest.of(page,perPage);

        LeaderBoardDTO leaderBoardDTO = leaderboardService.getLeaderboardOnPointScale(pointScaleName,pageable);
        leaderBoardDTO.setNextPage((long) (page+1));

        return new ResponseEntity<>(leaderBoardDTO, HttpStatus.OK);
    }
}
