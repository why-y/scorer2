package ch.sample.scorer2;

import org.junit.Test;

import static ch.sample.scorer2.Player.A;
import static ch.sample.scorer2.Player.B;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class GameTest {

    @Test
    public void  initialScoreIs0_0(){
        assertEquals("initial score must be 0:0",  "0:0", Game.start().print());
    }

    @Test
    public void from0_0scoreForAscoreGets15_0(){
        assertThat("score must be 15:0", Game.start().score(A).print(), is("15:0"));
    }

    @Test
    public void from15_0scoreForBscoreGets15_15(){
        assertThat("score must be 15:0",  Game.start().score(A).score(B).print(), is("15:15"));
    }

    @Test
    public void given30_0scoreForAscoreGets40_0() {
        assertThat("score must be 40:0", Game.start().score(A).score(A).score(A).print(), is("40:0"));
    }

    @Test
    public void given15_0scoreForAscoreGets30_0() {
        assertThat("score must be 30:0", Game.start().score(A).score(A).print(), is("30:0"));
    }

    @Test
    public void given0_40scoreForBscoreGetsGameB() {
        assertThat("score must be Game B", Game.start().score(B).score(B).score(B).score(B).print(), is("Game B"));
    }

    @Test
    public void given40_30scoreForBscoreGetsDeuce() {
        assertThat("score must be Deuce", Game.start().score(A).score(A).score(A).score(B).score(B).score(B).print(), is("Deuce"));
    }

    @Test
    public void givenDeuceScoreAmatchIsNotOver() {
        assertThat(Game.start().score(A).score(A).score(A).score(B).score(B).score(B).score(B).isOver(), is(false));
    }

    @Test
    public void given40_30scoreAgetsGameA() {
        assertThat("score must be Game A", Game.start().score(A).score(A).score(A).score(B).score(B).score(A).print(), is("Game A"));
    }

    @Test
    public void givenDeuceGameIsNotOver() {
        assertThat("Deuce must not be over!", Game.start().score(A).score(A).score(A).score(B).score(B).score(B).isOver(), is(false));
    }

    @Test
    public void testGameWonByA() {
        assertThat("Must be Game A!", Game.start().score(A).score(A).score(A).score(A).print(), is("Game A"));
    }

    @Test
    public void testGameWonAafterDeuce() {
        assertThat("Must be Game A!", Game.start().score(A).score(A).score(A)
                .score(B).score(B).score(B)
                .score(A).score(A).print(), is("Game A"));
    }

    @Test
    public void givenDeuceScoreBgetsAdvantageB() {
        assertThat(Game.start().score(A).score(B).score(A).score(B).score(A).score(B).score(B).print(), is("Advantage B"));
    }

    @Test(expected = AlreadyTerminatedException.class)
    public void givenGameOverAnotherScoreGetsException() {
        Game.start().score(A).score(A).score(A).score(A).score(B).score(B).score(B).score(B).print();
    }

}