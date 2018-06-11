package ch.sample.scorer2;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;

import org.junit.Test;

import static org.junit.Assert.*;

public class MatchScoreTest {

    private final static MatchScore SCORE_6_0_5_0_40_0 = MatchScore.start()
            .scoreA().scoreA().scoreA().scoreA() // 1:0
            .scoreA().scoreA().scoreA().scoreA() // 2:0
            .scoreA().scoreA().scoreA().scoreA() // 3:0
            .scoreA().scoreA().scoreA().scoreA() // 4:0
            .scoreA().scoreA().scoreA().scoreA() // 5:0
            .scoreA().scoreA().scoreA().scoreA() // 6:0
            .scoreA().scoreA().scoreA().scoreA() // 6:0 1:0
            .scoreA().scoreA().scoreA().scoreA() // 6:0 2:0
            .scoreA().scoreA().scoreA().scoreA() // 6:0 3:0
            .scoreA().scoreA().scoreA().scoreA() // 6:0 4:0
            .scoreA().scoreA().scoreA().scoreA() // 6:0 5:0
            .scoreA().scoreA().scoreA();         // 6:0 5:0 40:0

    @Test
    public void startMatch_scoreIs_0_0__0_0() {
        assertThat("the initial score of a match must be [0:0]-0:0", MatchScore.start().printFullScore(), equalTo("[0:0]-0:0"));
    }

    @Test
    public void givenNewMatch_aScores_gets_0_0__15_0() {
        assertThat(MatchScore.start().scoreA().printFullScore(), equalTo("[0:0]-15:0"));
    }

    @Test
    public void given_0_0__40_0_aScoresGets_1_0__0_0() {
        assertThat(MatchScore.start().scoreA().scoreA().scoreA().scoreA().printFullScore(), equalTo("[1:0]-0:0"));
    }

    @Test
    public void given_5_0__40_0_aScoresGets_6_0_0_0__0_0() {
        assertThat(MatchScore.start()
                .scoreA().scoreA().scoreA().scoreA()
                .scoreA().scoreA().scoreA().scoreA()
                .scoreA().scoreA().scoreA().scoreA()
                .scoreA().scoreA().scoreA().scoreA()
                .scoreA().scoreA().scoreA().scoreA()
                .scoreA().scoreA().scoreA().scoreA()
                .printFullScore(), equalTo("[6:0][0:0]-0:0"));
    }

    @Test(expected = AlreadyTerminatedException.class)
    public void given_MatchIsOverScoreAthrowsExc() {
        SCORE_6_0_5_0_40_0.scoreA() // Match over!!
        .scoreA();
    }

    @Test
    public void given_6_0_5_0__40_0_matchIsNotOver() {
        assertThat(SCORE_6_0_5_0_40_0.matchIsOver(), is(false));
    }

    @Test
    public void given_6_0_5_0__40_0_matchIsOver() {
        assertThat(SCORE_6_0_5_0_40_0.scoreA().matchIsOver(), is(true));
    }

    @Test
    public void given_6_0_5_0__40_0_aScoresGets_aWon_6_0_6_0() {
        assertThat(SCORE_6_0_5_0_40_0.scoreA()
                .printFullScore(), equalTo("[6:0][6:0] - Game, Set and Match Player A"));
    }

}