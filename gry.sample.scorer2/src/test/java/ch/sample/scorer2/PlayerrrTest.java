package ch.sample.scorer2;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerrrTest {

    @Test
    public void testInitPlayer() {
        assertThat(Playerrr.named("Bob Smith"), instanceOf(Playerrr.class));
    }

    @Test
    public void testPrintPlayersName() {
        assertThat(Playerrr.named("Eric Carter").printName(), equalTo("Eric Carter"));
    }

}