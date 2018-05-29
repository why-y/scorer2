package ch.sample.scorer2;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import org.junit.Test;

import static org.junit.Assert.*;

public class SetTest {

    private static final Set SCORE_4_0 = Set.start().scoreA().scoreA().scoreA().scoreA();
    private static final Set SCORE_4_4 = SCORE_4_0.scoreB().scoreB().scoreB().scoreB();

    @Test
    public void  initialScoreIs0_0(){
        assertThat("initial score must be 0:0", Set.start().print(), equalTo("0:0"));
    }

    @Test
    public void given0_0scoreAgets1_0(){
        assertThat("must be 1:0", Set.start().scoreA().print(), equalTo("1:0"));
    }

    @Test
    public void given1_0scoreAgets2_0(){
        assertThat("must be 2:0", Set.start().scoreA().scoreA().print(), equalTo("2:0"));
    }

    @Test
    public void given1_0scoreBgets1_1() {
        assertThat("must be 1:1", Set.start().scoreA().scoreB().print(), equalTo("1:1"));
    }

    @Test
    public void given5_0setIsNotOver() {
        assertThat("set must not be over", SCORE_4_0.scoreA().isOver(), is(false));
    }

    @Test
    public void given6_0setIsOver() {
        assertThat("set must be over", SCORE_4_0.scoreA().scoreA().isOver(), is(true));
    }

    @Test
    public void given6_5setIsNotOver() {
        assertThat("set must not be over", SCORE_4_4.scoreA().scoreB().scoreA().isOver(), is(false));
    }

    @Test
    public void given6_5inTiebreakModeSetIsOver() {
        assertThat("set must be over", Set.withTiebreak().start()
                .scoreA().scoreA().scoreA().scoreA().scoreA()
                .scoreB().scoreB().scoreB().scoreB().scoreB()
                .scoreA().isOver(),
                is(true));
    }

    @Test
    public void given7_5setIsOver() {
        assertThat("set must be over", SCORE_4_4.scoreA().scoreB().scoreA().scoreA().isOver(), is(true));
    }

    @Test
    public void given6_6scoreBgets6_7() {
        assertThat("set must 6:7", SCORE_4_4.scoreA().scoreB().scoreA().scoreB().scoreB().print(), equalTo("6:7"));
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