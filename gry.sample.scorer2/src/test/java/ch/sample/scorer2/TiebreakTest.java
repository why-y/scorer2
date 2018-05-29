package ch.sample.scorer2;

import org.junit.Test;

import static org.junit.Assert.*;

public class TiebreakTest {

    private final Tiebreak SCORE_5_5 = Tiebreak.start().scoreA().scoreB().scoreA().scoreB().scoreA().scoreB().scoreA().scoreB().scoreA().scoreB();

    @Test
    public void startTiebreakScoreIs0_0() {
        assertEquals("initial score must be 0:0", "0:0", Tiebreak.start().print());
    }

    @Test
    public void given0_0scoreAgets1_0() {
        assertEquals("must be 1:0", "1:0", Tiebreak.start().scoreA().print());
    }

    @Test
    public void given0_0scoreBgets0_1() {
        assertEquals("must be 0:1", "0:1", Tiebreak.start().scoreB().print());
    }

    @Test
    public void given1_0scoreAgets2_0() {
        assertEquals("must be 2:0", "2:0", Tiebreak.start().scoreA().scoreA().print());
    }

    @Test
    public void given6_5tiebreakIsNotOver() {
        assertFalse("on 6:5 the tiebreak must not be over", SCORE_5_5.scoreA().isOver());
    }

    @Test
    public void given7_5tiebreakIstOver() {
        assertTrue("on 7:5 the tiebreack must over", SCORE_5_5.scoreA().scoreA().isOver());
    }

    @Test
    public void given7_7scoreAgets8_7() {
        assertEquals("must be 8:7", "8:7", SCORE_5_5.scoreA().scoreB().scoreA().scoreB().scoreA().print());
    }

}