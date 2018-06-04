package ch.sample.scorer2;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    @Test
    public void testInitPlayer() {
        assertThat(Player.named("Bob Smith"), instanceOf(Player.class));
    }

    @Test
    public void testPrintPlayersName() {
        assertThat(Player.named("Eric Carter").printName(), equalTo("Eric Carter"));
    }

}