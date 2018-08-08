package ch.sample.scorer2.webapp;

import ch.sample.scorer2.Match;
import ch.sample.scorer2.MatchConfiguration;
import ch.sample.scorer2.Player;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static ch.sample.scorer2.MatchConfiguration.BestOf.FIVE;
import static ch.sample.scorer2.MatchConfiguration.BestOf.THREE;
import static ch.sample.scorer2.MatchConfiguration.Tiebreaks.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/tennis-score/api")
public class ScoreRestController {

    Map<String, Match> currentMatches = new HashMap<>();

    @RequestMapping(value="/match/{id}", method = GET)
    public Match getCurrentScore(@PathVariable String id) {
        System.out.println(String.format("getCurrentScore() of match: %s", id));
        Match currentMatch = getMatchById(id);
        return currentMatch != null ?
                currentMatch :
                Match.startDefaultMatch();
    }

    @RequestMapping(value="/match", method = POST)
    public String startMatch(@RequestBody String requestBody) {
        System.out.println(String.format("POST match -> start match -> RequestBody: %s", requestBody));
        MatchConfiguration matchConfig = isBestOfThree(requestBody) ? Match.bestOf(THREE) : Match.bestOf(FIVE);
        matchConfig = matchConfig.withTiebreaks(getTiebreakMode(requestBody));
        return registerMatch(matchConfig.start()).toString();
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

    @RequestMapping(value = "/match/{id}/rally", method = POST)
    public void scoreRallyFor(@PathVariable String id, @RequestBody String request) {
        System.out.println(String.format("scoreRallyFor() id: %s  request: %s", id, request));
        currentMatches.replace(id, getMatchById(id).score(getPlayer(request)));
    }

    private Match getMatchById(String id) {
        return currentMatches.get(id);
    }

    private Player getPlayer(String responseJson) {
        System.out.println("responseJson: " + responseJson);
        return responseJson.charAt(11) == 'A' ? Player.A : Player.B;
    }

    private UUID registerMatch(final Match newMatch) {
        UUID matchId = UUID.randomUUID();
        currentMatches.put(matchId.toString(), newMatch);
        return matchId;
    }

}