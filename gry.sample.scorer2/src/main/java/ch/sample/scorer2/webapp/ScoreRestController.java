package ch.sample.scorer2.webapp;

import ch.sample.scorer2.Match;
import ch.sample.scorer2.Player;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api")
public class ScoreRestController {

    Match currentMatch;

    @RequestMapping(value="/match", method = GET)
    public String getCurrentScore() {
        return currentMatch != null ?
                currentMatch.print() :
                "Match not yet started!";
    }

    @RequestMapping(value="/match", method = POST)
    public void startMatch(@RequestParam(value = "matchMode", defaultValue = "bestOfThree") String matchMode,
                             @RequestParam(value = "tiebreakMode", defaultValue = "inAllSets") String tiebreakMode) {
        currentMatch = Match.startDefaultMatch();
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