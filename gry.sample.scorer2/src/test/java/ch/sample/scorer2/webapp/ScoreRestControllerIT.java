package ch.sample.scorer2.webapp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ScoreRestControllerIT {

    @LocalServerPort
    private int port;

    private URL base;
    private URL matchApi;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
        this.matchApi = new URL(base.toString() + "tennis-score/api/match");
    }

    @Test
    public void givenStartDefaultMatchReturnsMatchId()  {
        UUID matchId = startNewMatch();
        assertThat(matchId, is(notNullValue()));
    }

    @Test
    public void givenNewMatch_getScore_is0_0()  {
        UUID matchId = startNewMatch();
        ResponseEntity<String> response = template.getForEntity( matchApi.toString() + "/" + matchId.toString(), String.class);
        String json = response.getBody();
        assertThat(matchIsOver(json), is(false));
        assertThat(getScore('A', json), is(0));
        assertThat(getScore('B', json), is(0));
        assertThat(hasTerminatedSets(json), is(false));
    }

    private int getScore(char player, String json) {
        String key = "score" + player;
        JsonParser jsonParser = new JacksonJsonParser();
        Map<String, Object> map = jsonParser.parseMap(json);
        Map<String, Object> currentSet = (Map<String, Object>) map.get("currentSet");
        Map<String, Object> currentScoreUnit = (Map<String, Object>) currentSet.get("currentScoreUnit");
        return currentScoreUnit.containsKey(key) ? Integer.valueOf((String)currentScoreUnit.get(key)) : -1;
    }

    private boolean matchIsOver(String json) {
        return json.contains("\"over\":true");
    }

    private boolean hasTerminatedSets(String json) {
        return !json.contains("\"terminatedScoreUnits\":[]");
    }

    private UUID startNewMatch()  {
        ResponseEntity<String> response = template.postForEntity(matchApi.toString(), "{}", String.class);
        return UUID.fromString(response.getBody());
    }
}