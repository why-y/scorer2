package ch.sample.scorer2;

import org.junit.Test;

import static org.junit.Assert.*;

public class SetTest {

    private static final Set SCORE_4_0 = Set.start().scoreA().scoreA().scoreA().scoreA();
    private static final Set SCORE_4_4 = SCORE_4_0.scoreB().scoreB().scoreB().scoreB();

    @Test
    public void  initialScoreIs0_0(){
        assertEquals("initial score must be 0:0",  "0:0", Set.start().print());
    }

    @Test
    public void given0_0scoreAgets1_0(){
        assertEquals("must be 1:0", "1:0", Set.start().scoreA().print());
    }

    @Test
    public void given1_0scoreAgets2_0(){
        assertEquals("must be 2:0", "2:0", Set.start().scoreA().scoreA().print());
    }

    @Test
    public void given1_0scoreBgets1_1() {
        assertEquals("must be 1:1", "1:1", Set.start().scoreA().scoreB().print());
    }

    @Test
    public void given5_0setIsNotOver() {
        assertFalse("set must not be over", SCORE_4_0.scoreA().isOver());
    }

    @Test
    public void given6_0setIsOver() {
        assertTrue("set must be over", SCORE_4_0.scoreA().scoreA().isOver());
    }

    @Test
    public void given6_5setIsNotOver() {
        assertFalse("set must not be over", SCORE_4_4.scoreA().scoreB().scoreA().isOver());
    }

    @Test
    public void given7_5setIsOver() {
        assertTrue("set must be over", SCORE_4_4.scoreA().scoreB().scoreA().scoreA().isOver());
    }
    @Test
    public void given6_6scoreBgets6_7() {
        assertEquals("set must 6:7", "6:7", SCORE_4_4.scoreA().scoreB().scoreA().scoreB().scoreB().print());
    }

    @Test(expected = AlreadyTerminatedException.class)
    public void givenSetOverAnotherScoreAGetsException() {
        SCORE_4_4.scoreA().scoreA().scoreA();
    }

    @Test(expected = AlreadyTerminatedException.class)
    public void givenSetOverAnotherScoreBGetsException() {
        SCORE_4_4.scoreA().scoreA().scoreB();
    }


}