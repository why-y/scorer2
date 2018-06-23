package ch.sample.scorer2;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import org.junit.Test;

import static org.junit.Assert.*;
import static ch.sample.scorer2.Player.*;

public class SetTest {

    private enum Player {A, B};

    private Set scoreNRalliesFor(Set set, int numOfRallies, Player player) {
        for (int i = 0; i < numOfRallies; i++) {
            set = player == Player.A ? set.score(A) : set.score(B);
        }
        return set;
    }

    private Set scoreNGamesFor(Set set, int numOfGames, Player player) {
        for (int i = 0; i < numOfGames; i++) {
            set = scoreNRalliesFor(set, 4, player);
        }
        return set;
    }

    @Test
    public void  initialScoreIs0_0(){
        assertThat("initial score must be 0:0", Set.start().print(), equalTo("0:0 0:0"));
    }

    @Test
    public void given0_0scoreAgets1_0(){
        assertThat("must be 1:0",  scoreNGamesFor(Set.start(), 1, Player.A).print(), equalTo("1:0 0:0"));
    }

    @Test
    public void given1_0scoreAgets2_0(){
        assertThat("must be 2:0", scoreNGamesFor(Set.start(), 2, Player.A).print(), equalTo("2:0 0:0"));
    }

    @Test
    public void given1_0scoreBgets1_1() {
        Set testee = scoreNGamesFor(Set.start(), 1, Player.A);
        testee = scoreNGamesFor(testee, 1, Player.B);
        assertThat("must be 1:1", testee.print(), equalTo("1:1 0:0"));
    }

    @Test
    public void given5_0setIsNotOver() {
        assertThat("set must not be over", scoreNGamesFor(Set.start(), 5, Player.A).isOver(), is(false));
    }

    @Test
    public void given6_0setIsOver() {
        assertThat("set must be over", scoreNGamesFor(Set.start(), 6, Player.A).isOver(), is(true));
    }

    @Test
    public void given6_5setIsNotOver() {
        Set testee = scoreNGamesFor(Set.start(), 5, Player.B);
        testee = scoreNGamesFor(testee, 6, Player.A);
        assertThat(testee.isOver(), is(false));
    }

    @Test
    public void given6_6inTiebreakModeSetIsNotOver() {
        Set testee = scoreNGamesFor(Set.start(), 5, Player.B);
        testee = scoreNGamesFor(testee, 6, Player.A);
        testee = scoreNGamesFor(testee, 1, Player.B);
        assertThat(testee.isOver(), is(false));
    }

    @Test
    public void given7_6inTiebreakModeSetIstOver() {
        Set testee = scoreNGamesFor(Set.start(), 5, Player.B);
        testee = scoreNGamesFor(testee, 6, Player.A);
        testee = scoreNGamesFor(testee, 1, Player.B);
        testee = scoreNRalliesFor(testee, 7, Player.A);
        assertThat(testee.isOver(), is(true));
    }

    @Test
    public void given7_5setIsOver() {
        Set testee = scoreNGamesFor(Set.start(), 5, Player.B);
        testee = scoreNGamesFor(testee, 7, Player.A);
        assertThat(testee.isOver(), is(true));
    }

    @Test
    public void given6_6scoreBgets6_7() {
        Set testee = scoreNGamesFor(Set.withoutTiebreak().start(), 5, Player.B);
        testee = scoreNGamesFor(testee, 6, Player.A);
        testee = scoreNGamesFor(testee, 2, Player.B);
        assertThat(testee.print(), equalTo("6:7 0:0"));
    }

    @Test
    public void given6_6inDefaultModeRequiresTiebreak() {
        Set testee = scoreNGamesFor(Set.start(), 5, Player.A);
        testee = scoreNGamesFor(testee, 6, Player.B);
        testee = scoreNGamesFor(testee, 1, Player.A);
        assertThat(testee.requiresTiebreak(), is(true));
    }

    @Test
    public void given6_6inAdvantageModeRequiresNoTiebreak() {
        Set testee = scoreNGamesFor(Set.withoutTiebreak().start(), 5, Player.A);
        testee = scoreNGamesFor(testee, 6, Player.B);
        assertThat(testee.score(A).requiresTiebreak(), is(false));
    }

    @Test(expected = AlreadyTerminatedException.class)
    public void givenSetOverAnotherScoreAGetsException() {
        scoreNGamesFor(Set.start(), 6, Player.A).score(A);
    }

    @Test(expected = AlreadyTerminatedException.class)
    public void givenSetOverAnotherScoreBGetsException() {
        scoreNGamesFor(Set.start(), 6, Player.A).score(B);
    }

}