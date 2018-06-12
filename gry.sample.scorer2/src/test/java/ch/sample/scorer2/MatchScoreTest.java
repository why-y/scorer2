package ch.sample.scorer2;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import ch.sample.scorer2.MatchScore.Mode;
import static ch.sample.scorer2.MatchScore.Mode.*;

import org.junit.Test;

import static org.junit.Assert.*;

public class MatchScoreTest {

    private final MatchScore scoreTo_6_0_6_0_5_0_40_0() {
        return scoreTo_6_0_5_0_40_0(BEST_OF_5).scoreA() // 6:0 6:0
                .scoreA().scoreA().scoreA().scoreA() //  6:0 6:0 1:0
                .scoreA().scoreA().scoreA().scoreA() // 6:0 6:0 2:0
                .scoreA().scoreA().scoreA().scoreA() // 6:0 6:0 3:0
                .scoreA().scoreA().scoreA().scoreA() // 6:0 6:0 4:0
                .scoreA().scoreA().scoreA().scoreA() // 6:0 6:0 5:0
                .scoreA().scoreA().scoreA();         // 6:0 6:0 5:0 40:0

    }

    private final MatchScore scoreTo_6_0_5_0_40_0(Mode mode) {
        return MatchScore.start(mode)
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
    }

    private final MatchScore scoreTo_0_6_0_5_0_40(Mode mode) {
        return MatchScore.start(mode)
                .scoreB().scoreB().scoreB().scoreB() // 0:1
                .scoreB().scoreB().scoreB().scoreB() // 0:2
                .scoreB().scoreB().scoreB().scoreB() // 0:3
                .scoreB().scoreB().scoreB().scoreB() // 0:4
                .scoreB().scoreB().scoreB().scoreB() // 0:5
                .scoreB().scoreB().scoreB().scoreB() // 0:6
                .scoreB().scoreB().scoreB().scoreB() // 0:6 0:1
                .scoreB().scoreB().scoreB().scoreB() // 0:6 0:2
                .scoreB().scoreB().scoreB().scoreB() // 0:6 0:3
                .scoreB().scoreB().scoreB().scoreB() // 0:6 0:4
                .scoreB().scoreB().scoreB().scoreB() // 0:6 0:5
                .scoreB().scoreB().scoreB();         // 0:6 0:5 0:40
    }

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
        scoreTo_6_0_5_0_40_0(BEST_OF_3).scoreA() // Match over!!
        .scoreA();
    }

    @Test
    public void given_6_0_5_0__40_0_matchIsNotOver() {
        assertThat(scoreTo_6_0_5_0_40_0(BEST_OF_3).matchIsOver(), is(false));
    }

    @Test
    public void given_6_0_5_0__40_0_matchIsOver() {
        assertThat(scoreTo_6_0_5_0_40_0(BEST_OF_3).scoreA().matchIsOver(), is(true));
    }

    @Test
    public void given_6_0_5_0__40_0_aScoresGets_aWon_6_0_6_0() {
        assertThat(scoreTo_6_0_5_0_40_0(BEST_OF_3).scoreA()
                .printFullScore(), equalTo("[6:0][6:0] - Game, Set and Match Player A"));
    }

    @Test
    public void given_0_6_0_5_0_40_bScores_gets_bWon_0_6_0_6() {
        assertThat(scoreTo_0_6_0_5_0_40(BEST_OF_3).scoreB().printFullScore(), equalTo("[0:6][0:6] - Game, Set and Match Player B"));
    }

    @Test
    public void given_bestOfFive_6_0_6_0_isNotOver() {
        assertThat(scoreTo_6_0_5_0_40_0(BEST_OF_5).scoreA().matchIsOver(), is(false));
    }

    @Test
    public void given_bestOfFive_6_0_6_0_5_0_40_0_isNotOver() {
        assertThat(scoreTo_6_0_6_0_5_0_40_0()
                .matchIsOver(), is(false));
    }

    @Test
    public void given_bestOfFive_6_0_6_0_6_0_isOver() {
        assertThat(scoreTo_6_0_6_0_5_0_40_0().scoreA()
                .matchIsOver(), is(true));
    }

}