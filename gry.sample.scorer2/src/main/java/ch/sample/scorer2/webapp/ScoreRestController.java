package ch.sample.scorer2.webapp;

import ch.sample.scorer2.Match;
import ch.sample.scorer2.MatchConfiguration;
import ch.sample.scorer2.Player;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ch.sample.scorer2.MatchConfiguration.BestOf.FIVE;
import static ch.sample.scorer2.MatchConfiguration.BestOf.THREE;
import static ch.sample.scorer2.MatchConfiguration.Tiebreaks.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api")
public class ScoreRestController {

    Match currentMatch;

    @RequestMapping(value="/match", method = GET)
    public Match getCurrentScore() {
        return currentMatch != null ?
                currentMatch :
                Match.startDefaultMatch();
    }

    @RequestMapping(value="/match", method = POST)
    public void startMatch(@RequestBody String requestBody) {
        System.out.println(String.format("POST match -> start match -> RequestBody: %s", requestBody));
        MatchConfiguration matchConfig = isBestOfThree(requestBody) ? Match.bestOf(THREE) : Match.bestOf(FIVE);
        matchConfig = matchConfig.withTiebreaks(getTiebreakMode(requestBody));
        currentMatch = matchConfig.start();
    }

    private MatchConfiguration.Tiebreaks getTiebreakMode(String requestBody) {
        if(requestBody.contains("all-but-last-set")) {
            return IN_ALL_BUT_THE_FINAL_SET;
        } else if (requestBody.contains("in-no-set")) {
            return IN_NO_SET;
        } else {
            return IN_ALL_SETS;
        }
    }

    private boolean isBestOfThree(String requestBody) {
        return requestBody.contains("best-of-3");
    }

    @RequestMapping(value = "/match/rally", method = POST)
    public void scoreRallyFor(@RequestBody String request) {
        currentMatch = currentMatch.score(getPlayer(request));
    }

    private Player getPlayer(String responseJson) {
        System.out.println("responseJson: " + responseJson);
        return responseJson.charAt(11) == 'A' ? Player.A : Player.B;
    }

}