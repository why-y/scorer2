package ch.sample.scorer2;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import static ch.sample.scorer2.MatchConfiguration.BestOf.*;
import static ch.sample.scorer2.MatchConfiguration.Tiebreaks.*;


public class MatchConfigurationTest {

    @Test
    public void given_startStandardMatch_isBestOf3_withTiebreaksInAllSets() {
        assertThat(new MatchConfiguration().start().isBestOf(THREE), is(true));
    }

    @Test
    public void test_MatchConfig_withBestOf5() {
        assertThat(new MatchConfiguration().bestOf(MatchConfiguration.BestOf.FIVE).start().isBestOf(FIVE), is(true));
    }

}