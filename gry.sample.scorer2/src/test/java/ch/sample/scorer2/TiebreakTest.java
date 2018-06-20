package ch.sample.scorer2;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;

import org.junit.Test;

import static org.junit.Assert.*;
import static ch.sample.scorer2.Player.*;

public class TiebreakTest {

    private final Tiebreak SCORE_5_5 = Tiebreak.start().score(A).score(B).score(A).score(B).score(A).score(B).score(A).score(B).score(A).score(B);

    @Test
    public void startTiebreakScoreIs0_0() {
        assertThat(Tiebreak.start().print(), equalTo("(0:0)"));
    }

    @Test
    public void given0_0scoreAgets1_0() {
        assertThat(Tiebreak.start().score(A).print(), equalTo("(1:0)"));
    }

    @Test
    public void given0_0scoreBgets0_1() {
        assertThat(Tiebreak.start().score(B).print(), equalTo("(0:1)"));
    }

    @Test
    public void given1_0scoreAgets2_0() {
        assertThat(Tiebreak.start().score(A).score(A).print(), equalTo("(2:0)"));
    }

    @Test
    public void given6_5tiebreakIsNotOver() {
        assertThat("on 6:5 the tiebreak must not be over", SCORE_5_5.score(A).isOver(), is(false));
    }

    @Test
    public void given4_0tiebreakIsNotOver() {
        assertThat("on 4:0 the tiebreak must not be over", Tiebreak.start().score(A).score(A).score(A).score(A).isOver(), is(false));
    }

    @Test
    public void given7_5tiebreakIstOver() {
        assertThat("on 7:5 the tiebreack must over", SCORE_5_5.score(A).score(A).isOver(), is(true));
    }

    @Test
    public void given7_7scoreAgets8_7() {
        assertThat(SCORE_5_5.score(A).score(B).score(A).score(B).score(A).print(), equalTo("(8:7)"));
    }

}