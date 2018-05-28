package ch.sample.scorer2;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void  initialScoreIs0_0(){
        assertEquals("initial score must be 0:0",  "0:0", Game.start().print());
    }

    @Test
    public void from0_0scoreForAscoreGets15_0(){
        assertEquals("score must be 15:0", "15:0", Game.start().scoreA().print());
    }

    @Test
    public void from15_0scoreForBscoreGets15_15(){
        assertEquals("score must be 15:0", "15:15", Game.start().scoreA().scoreB().print());
    }

    @Test
    public void given15_0scoreForAscoreGets30_0() {
        assertEquals("score must be 30:0", "30:0", Game.start().scoreA().scoreA().print());
    }

    @Test
    public void given30_0scoreForAscoreGets40_0() {
        assertEquals("score must be 40:0", "40:0", Game.start().scoreA().scoreA().scoreA().print());
    }

    @Test
    public void given40_30scoreForBscoreGetsDeuce() {
        assertEquals("score must be Deuce", "Deuce", Game.start().scoreA().scoreA().scoreA().scoreB().scoreB().scoreB().print());
    }

    @Test
    public void givenDeuceScoreAgetsAdvantageA() {
        assertEquals("score must be Advantage A", "Advantage A", Game.start().scoreA().scoreA().scoreA().scoreB().scoreB().scoreB().scoreA().print());
    }

    @Test
    public void given40_30scoreAgetsGameA() {
        assertEquals("score must be Game A", "Game A", Game.start().scoreA().scoreA().scoreA().scoreB().scoreB().scoreA().print());
    }

    @Test(expected = AlreadyTerminatedException.class)
    public void givenGameOverAnotherScoreGetsException() {
        Game.start().scoreA().scoreA().scoreA().scoreA().scoreB().scoreB().scoreB().scoreB().print();
    }

}