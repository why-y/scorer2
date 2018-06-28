package ch.sample.scorer2;

import org.junit.Test;

import static org.hamcrest.core.Is.isA;
import static org.junit.Assert.assertThat;

public class RallyTest {

    @Test
    public void testRallyCreation() {
        assertThat(Rally.wonBy(Player.A), isA(Rally.class));
    }

}