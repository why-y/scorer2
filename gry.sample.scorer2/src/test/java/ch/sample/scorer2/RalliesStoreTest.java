package ch.sample.scorer2;

import org.junit.Test;

import static ch.sample.scorer2.Player.A;
import static ch.sample.scorer2.Player.B;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.Is.isA;
import static org.junit.Assert.assertThat;

public class RalliesStoreTest {

    @Test
    public void testCreation() {
        assertThat(RalliesStore.start(), isA(RalliesStore.class));
    }

    @Test
    public void given_newLog_addRallyWonByA_hasOneEntry() {
        assertThat(RalliesStore.start().add(Rally.wonBy(A)).noOfRallies(), is(1));
    }

    @Test
    public void testReplayRalliesSequence() {
        RalliesStore testee =  RalliesStore.start().
                add(Rally.wonBy(A)).
                add(Rally.wonBy(B)).add(Rally.wonBy(B));
        final StringBuilder rallyWinnerSequenceStr = new StringBuilder();
        testee.streamRallySequence().map(rally -> rally.getWinner()).forEach(player -> rallyWinnerSequenceStr.append(player));
        assertThat(rallyWinnerSequenceStr.toString(), is("ABB"));
    }

    @Test
    public void testApplyToDefaultMatch() {
        RalliesStore testee =  RalliesStore.start().
                add(Rally.wonBy(A)).
                add(Rally.wonBy(B)).add(Rally.wonBy(B));
        assertThat(testee.applyToMatch(Match.startDefaultMatch()).print(), is("0:0 15:30"));
    }

}