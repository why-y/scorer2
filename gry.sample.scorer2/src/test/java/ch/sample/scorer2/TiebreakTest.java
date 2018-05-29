package ch.sample.scorer2;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;

import org.junit.Test;

import static org.junit.Assert.*;

public class TiebreakTest {

    private final Tiebreak SCORE_5_5 = Tiebreak.start().scoreA().scoreB().scoreA().scoreB().scoreA().scoreB().scoreA().scoreB().scoreA().scoreB();

    @Test
    public void startTiebreakScoreIs0_0() {
        assertThat(Tiebreak.start().print(), equalTo("0:0"));
    }

    @Test
    public void given0_0scoreAgets1_0() {
        assertThat(Tiebreak.start().scoreA().print(), equalTo("1:0"));
    }

    @Test
    public void given0_0scoreBgets0_1() {
        assertThat(Tiebreak.start().scoreB().print(), equalTo("0:1"));
    }

    @Test
    public void given1_0scoreAgets2_0() {
        assertThat(Tiebreak.start().scoreA().scoreA().print(), equalTo("2:0"));
    }

    @Test
    public void given6_5tiebreakIsNotOver() {
        assertThat("on 6:5 the tiebreak must not be over", SCORE_5_5.scoreA().isOver(), is(false));
    }

    @Test
    public void given4_0tiebreakIsNotOver() {
        assertThat("on 4:0 the tiebreak must not be over", Tiebreak.start().scoreA().scoreA().scoreA().scoreA().isOver(), is(false));
    }

    @Test
    public void given7_5tiebreakIstOver() {
        assertThat("on 7:5 the tiebreack must over", SCORE_5_5.scoreA().scoreA().isOver(), is(true));
    }

    @Test
    public void given7_7scoreAgets8_7() {
        assertThat(SCORE_5_5.scoreA().scoreB().scoreA().scoreB().scoreA().print(), equalTo("8:7"));
    }

}