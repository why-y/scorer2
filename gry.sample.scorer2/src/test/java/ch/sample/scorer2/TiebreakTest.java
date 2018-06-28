package ch.sample.scorer2;

import org.junit.Test;

import static ch.sample.scorer2.Player.A;
import static ch.sample.scorer2.Player.B;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class TiebreakTest {

    private Tiebreak scoreNRalliesFor(Tiebreak tiebreak, int numOfRallies, Player player) {
        for (int i = 0; i < numOfRallies; i++) {
            tiebreak = player == Player.A ? tiebreak.score(A) : tiebreak.score(B);
        }
        return tiebreak;
    }

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
        Tiebreak testee = scoreNRalliesFor(Tiebreak.start(), 5, B);
        testee = scoreNRalliesFor(testee, 6, A);
        assertThat("on 6:5 the tiebreak must not be over", testee.isOver(), is(false));
    }

    @Test
    public void given4_0tiebreakIsNotOver() {
        assertThat("on 4:0 the tiebreak must not be over", Tiebreak.start().score(A).score(A).score(A).score(A).isOver(), is(false));
    }

    @Test
    public void given7_5tiebreakIstOver() {
        Tiebreak testee = scoreNRalliesFor(Tiebreak.start(), 5, B);
        testee = scoreNRalliesFor(testee, 6, A);
        assertThat("on 7:5 the tiebreack must over", testee.score(A).isOver(), is(true));
    }

    @Test
    public void given7_7scoreAgets8_7() {
        Tiebreak testee = scoreNRalliesFor(Tiebreak.start(), 5, B);
        testee = scoreNRalliesFor(testee, 6, A);
        assertThat(testee.score(B).score(A).score(B).score(A).print(), equalTo("(8:7)"));
    }

    @Test
    public void given8_7scoreAgetsWonByA() {
        Tiebreak testee = scoreNRalliesFor(Tiebreak.start(), 5, B);
        testee = scoreNRalliesFor(testee, 6, A);
        assertThat(testee.score(B).score(A).score(B).score(A).score(A).isWonBy(A), equalTo(true));
    }

}