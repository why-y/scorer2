package ch.sample.scorer2;

import static org.hamcrest.core.IsEqual.equalTo;

import org.junit.Test;

import static org.junit.Assert.*;

public class MatchTest {

    @Test
    public void startMatch_scoreIs_0_0__0_0() {
        assertThat("the initial score of a match must be [0:0]-0:0", Match.start().print(), equalTo("[0:0]-0:0"));
    }

    @Test
    public void givenNewMatch_aScores_gets_0_0__15_0() {
        assertThat(Match.start().scoreA().print(), equalTo("[0:0]-15:0"));
    }

    @Test
    public void given_0_0__40_0_aScoresGets_1_0__0_0() {
        assertThat(Match.start().scoreA().scoreA().scoreA().scoreA().print(), equalTo("[1:0]-0:0"));
    }

    @Test
    public void given_5_0__40_0_aScoresGets_6_0_0_0__0_0() {
        assertThat(Match.start()
                .scoreA().scoreA().scoreA().scoreA()
                .scoreA().scoreA().scoreA().scoreA()
                .scoreA().scoreA().scoreA().scoreA()
                .scoreA().scoreA().scoreA().scoreA()
                .scoreA().scoreA().scoreA().scoreA()
                .scoreA().scoreA().scoreA().scoreA()
                .print(), equalTo("[6:0][0:0]-0:0"));
    }

}