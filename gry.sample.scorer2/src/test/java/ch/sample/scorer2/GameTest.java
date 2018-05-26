package ch.sample.scorer2;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void  initialScoreIs0_0(){
        assertEquals("initial score must be 0:0",  "0:0", Game.start().toString());
    }

    @Test
    public void from0_0scoreForAscoreGets15_0(){
        assertEquals("score must be 15:0", "15:0", Game.start().scoreA().toString());
    }

    @Test
    public void from15_0scoreForBscoreGets15_15(){
        assertEquals("score must be 15:0", "15:15", Game.start().scoreA().scoreB().toString());
    }

    @Test
    public void from15_0scoreForAscoreGets30_0() {
        assertEquals("score must be 30:0", "30:0", Game.start().scoreA().scoreA().toString());
    }

    @Test
    public void from30_0scoreForAscoreGets40_0() {
        assertEquals("score must be 40:0", "40:0", Game.start().scoreA().scoreA().scoreA().toString());
    }

    @Test
    public void from40_30scoreForBscoreGetsDeuce() {
        assertEquals("score must be Deuce", "Deuce", Game.start().scoreA().scoreA().scoreA().scoreB().scoreB().scoreB().toString());
    }

    @Test
    public void fromDeuceScoreAgetsAdvantageA() {
        assertEquals("score must be Advantage A", "Advantage A", Game.start().scoreA().scoreA().scoreA().scoreB().scoreB().scoreB().scoreA().toString());
    }

    @Test
    public void from40_30scoreAGetsGameA() {
        assertEquals("score must be Game A", "Game A", Game.start().scoreA().scoreA().scoreA().scoreB().scoreB().scoreA().toString());
    }

    @Test(expected = RuntimeException.class)
    public void cannotScoreOnTerminatedGame() {
        assertEquals("must not score on terminated game!", "Not Allowed!", Game.start().scoreA().scoreA().scoreA().scoreA().scoreB().scoreB().scoreB().scoreB().toString());
    }

}