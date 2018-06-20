package ch.sample.scorer2;
import ch.sample.scorer2.MatchConfiguration.BestOf;
import ch.sample.scorer2.MatchConfiguration.Tiebreaks;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.Is.is;
import static ch.sample.scorer2.MatchConfiguration.BestOf.*;
import static ch.sample.scorer2.MatchConfiguration.Tiebreaks.*;
import static org.junit.Assert.*;

import org.junit.Test;


public class MatchTest {

    private enum Player {A, B};

    private Match scoreNRalliesFor(Match match, int numOfRallies, Player player) {
        for (int i = 0; i < numOfRallies; i++) {
            match = player == Player.A ? match.scoreA() : match.scoreB();
        }
        return match;
    }

    private Match scoreNGamesFor(Match match, int numOfGames, Player player) {
        for (int i = 0; i < numOfGames; i++) {
            match = scoreNRalliesFor(match, 4, player);
        }
        return match;
    }

    @Test
    public void startMatch_scoreIs_0_0__0_0() {
        assertThat( Match.startDefaultMatch().printFullScore(), equalTo("[0:0] 0:0"));
    }

    @Test
    public void givenNewMatch_aScores_gets_0_0__15_0() {
        assertThat(Match.startDefaultMatch().scoreA().printFullScore(), equalTo("[0:0] 15:0"));
    }

    @Test
    public void given_0_0__40_0_aScoresGets_1_0__0_0() {
        assertThat(Match.startDefaultMatch().scoreA().scoreA().scoreA().scoreA().printFullScore(), equalTo("[1:0] 0:0"));
    }

    @Test
    public void given_5_0__40_0_aScoresGets_6_0_0_0__0_0() {
        Match testee = scoreNGamesFor(Match.startDefaultMatch(), 5, Player.A);
        testee = scoreNRalliesFor(testee, 3, Player.A);
        assertThat(testee.scoreA()
                .printFullScore(), equalTo("[6:0][0:0] 0:0"));
    }

    @Test(expected = AlreadyTerminatedException.class)
    public void given_MatchIsOverScoreAthrowsExc() {
        scoreNGamesFor(Match.startDefaultMatch(), 6+6, Player.A)
                .scoreA();
    }

    @Test
    public void given_6_0_5_0_40_0_matchIsNotOver() {
        Match testee = scoreNGamesFor(Match.startDefaultMatch(), 6+5, Player.A);
        testee = scoreNRalliesFor(testee, 3, Player.A);
        assertThat(testee.matchIsOver(), is(false));
    }

    @Test
    public void given_6_0_5_0_40_0_aScores_matchIsOver() {
        Match testee = scoreNGamesFor(Match.startDefaultMatch(), 6+5, Player.A);
        testee = scoreNRalliesFor(testee, 3, Player.A);
        assertThat(testee.scoreA().matchIsOver(), is(true));
    }

    @Test
    public void given_6_0_5_0_40_0_aScoresGets_aWon_6_0_6_0() {
        Match testee = scoreNGamesFor(Match.startDefaultMatch(), 6+5, Player.A);
        testee = scoreNRalliesFor(testee, 3, Player.A);
        assertThat(testee.scoreA().printFullScore(), equalTo("[6:0][6:0] - Game, Set and Match Player A"));
    }

    @Test
    public void given_0_6_0_5_0_40_bScores_gets_bWon_0_6_0_6() {
        assertThat(scoreNGamesFor(Match.startDefaultMatch(), 6+6, Player.B).printFullScore(),
                equalTo("[0:6][0:6] - Game, Set and Match Player B"));
    }

    @Test
    public void given_bestOfFive_6_0_6_0_isNotOver() {
        assertThat(scoreNGamesFor(Match.bestOf(FIVE).start(), 6+6, Player.A)
                        .matchIsOver(),
                is(false));
    }

    @Test
    public void given_bestOfFive_6_0_6_0_5_0_40_0_isNotOver() {
        assertThat(scoreNGamesFor(Match.bestOf(FIVE).start(), 6+6+5, Player.A)
                .scoreA().scoreA().scoreA()
                .matchIsOver(), is(false));
    }

    @Test
    public void given_bestOfFive_6_0_6_0_6_0_isOver() {
        assertThat(scoreNGamesFor(Match.bestOf(FIVE).start(), 6+6+6, Player.A)
                .matchIsOver(), is(true));
    }

    @Test
    public void testIsDefaultMatchBestOfThree() {
        assertThat(Match.startDefaultMatch().isBestOf(THREE), is(true));
    }

    @Test
    public void testIsBestOfFive() {
        assertThat(Match.bestOf(FIVE).start().isBestOf(FIVE), is(true));
    }

    @Test
    public void given6_6scoreA_gets_6_6_1_0_inTiebreak() {
        Match testee =  Match.startDefaultMatch();
        testee = scoreNGamesFor(testee, 5, Player.B);
        testee = scoreNGamesFor(testee, 6, Player.A);
        testee = scoreNGamesFor(testee, 1, Player.B);
        assertThat(testee.scoreA().printFullScore(), is("[6:6](1:0)"));
    }

    @Test
    public void given6_6and7_0_inTiebreak_setIsWonByA() {
        Match testee =  Match.startDefaultMatch();
        testee = scoreNGamesFor(testee, 5, Player.B);
        testee = scoreNGamesFor(testee, 6, Player.A);
        testee = scoreNGamesFor(testee, 1, Player.B);
        testee = scoreNRalliesFor(testee,7, Player.A);
        assertThat(testee.printFullScore(), is("[7:6][0:0] 0:0"));
    }

    @Test
    public void testBestOfThreeMatchWithoutTiebreaks() {
        Match testee = Match.withTiebreaks(IN_NO_SET).start();
        testee = scoreNGamesFor(testee, 5, Player.A);
        testee = scoreNGamesFor(testee, 6, Player.B); // 5:6
        testee = scoreNGamesFor(testee, 2, Player.A); // 7:6
        assertThat(testee.matchIsOver(), is(false));
        testee = scoreNGamesFor(testee, 3, Player.B); // [7:9] 0:0
        assertThat(testee.matchIsOver(), is(false));
        testee = scoreNGamesFor(testee, 5, Player.A);
        testee = scoreNGamesFor(testee, 6, Player.B); // [7:9] 5:6
        testee = scoreNGamesFor(testee, 3, Player.A); // [7:9] [8:6] 0:0
        assertThat(testee.matchIsOver(), is(false));
        testee = scoreNGamesFor(testee, 5, Player.A);
        testee = scoreNGamesFor(testee, 6, Player.B); // [7:9] [8:6] 5:6
        testee = scoreNGamesFor(testee, 2, Player.A); // [7:9] [8:6] 7:6
        testee = scoreNGamesFor(testee, 2, Player.B); // [7:9] [8:6] 7:8
        testee = scoreNGamesFor(testee, 2, Player.A); // [7:9] [8:6] 9:8
        testee = scoreNGamesFor(testee, 2, Player.B); // [7:9] [8:6] 9:10
        testee = scoreNGamesFor(testee, 2, Player.A); // [7:9] [8:6] 11:10
        testee = scoreNGamesFor(testee, 3, Player.B); // [7:9] [8:6] 11:13
        assertThat(testee.matchIsOver(), is(true));
        assertThat(testee.printFullScore(), is("[7:9][8:6][11:13] - Game, Set and Match Player B"));
    }

}