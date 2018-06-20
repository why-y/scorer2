package ch.sample.scorer2;

import java.util.ArrayList;
import java.util.List;
import ch.sample.scorer2.MatchConfiguration.BestOf;
import ch.sample.scorer2.MatchConfiguration.Tiebreaks;
import static ch.sample.scorer2.MatchConfiguration.BestOf.*;
import static ch.sample.scorer2.MatchConfiguration.Tiebreaks.*;
import static ch.sample.scorer2.Player.*;

public class Match {

    private static final BestOf DEFAUL_MODE = THREE;
    private static final Tiebreaks DEFAULT_TIEBREAKS = IN_ALL_SETS;

    private final BestOf matchMode;
    private final Tiebreaks tiebreakMode;
    private final List<Set> terminatedSets;
    private final Set currentSet;
    private final ScoreUnit currentScoreUnit;

    private Match(BestOf matchMode, Tiebreaks tiebreakMode, final List<Set> terminatedSets, Set currentSet, ScoreUnit currentScoreUnit) {
        this.matchMode = matchMode;
        this.tiebreakMode = tiebreakMode;
        this.terminatedSets = terminatedSets;
        this.currentSet = currentSet;
        this.currentScoreUnit = currentScoreUnit;
    }

    static Match __start(BestOf matchMode, Tiebreaks tiebreakMode) {
        Set startSet = tiebreakMode == Tiebreaks.IN_NO_SET ? Set.withoutTiebreak().start() : Set.withTiebreak().start();
        return new Match(matchMode, tiebreakMode, new ArrayList<>(matchMode == THREE ? 3 : 5), startSet, Game.start());
    }

    public static Match startDefaultMatch() {
        return __start(DEFAUL_MODE, DEFAULT_TIEBREAKS);
    }

    public static MatchConfiguration withTiebreaks(Tiebreaks tiebreakMode) {
        return new MatchConfiguration().withTiebreaks(tiebreakMode);
    }

    public static MatchConfiguration bestOf(BestOf maxNoOfSets) {
        return new MatchConfiguration().bestOf(maxNoOfSets);
    }

    public Match score(Player player) {
        assertMatchNotOver();
        ScoreUnit updatedScoreUnit = currentScoreUnit.score(player);
        if(updatedScoreUnit.isOver()) {
            return updateSetAndStartNewGame(currentSet.score(player));
        }
        return updateCurrentScoreUnit(updatedScoreUnit);
    }

    public boolean matchIsOver() {
        return aHasWon() || bHasWon();
    }

    public boolean isBestOf(BestOf matchMode) {
        return this.matchMode == matchMode;
    }

    public String printFullScore() {
        return matchIsOver()
                ? String. format("%s - Game, Set and Match Player %s", printTerminatedSets(), aHasWon() ? "A" : "B")
                : String.format("%s%s %s", printTerminatedSets(), printSet(currentSet), currentScoreUnit.print());
    }

    private boolean aHasWon() {
        return terminatedSets.stream().filter(set -> set.aHasWon()).count() >= noOfSetsToWin();
    }

    private boolean bHasWon() {
        return terminatedSets.stream().filter(set -> set.bHasWon()).count() >= noOfSetsToWin();
    }

    private Match updateCurrentScoreUnit(final ScoreUnit updatedScoreUnit) {
        return new Match(matchMode, tiebreakMode, terminatedSets, currentSet, updatedScoreUnit);
    }

    private Match updateSetAndStartNewGame(final Set updatedSet) {
        if(updatedSet.requiresTiebreak()) {
            return updateSetAndStartTiebreak(updatedSet);
        }
        return updatedSet.isOver() ?
                updateTerminatedSetsAndStartNewSet(updatedSet) :
                new Match(matchMode, tiebreakMode, terminatedSets, updatedSet, Game.start());
    }

    private Match updateSetAndStartTiebreak(final Set updatedSet) {
        return new Match(matchMode, tiebreakMode, terminatedSets, updatedSet, Tiebreak.start());
    }

    private Match updateTerminatedSetsAndStartNewSet(final Set terminatedSet) {
        return new Match(matchMode, tiebreakMode, addTerminatedSet(terminatedSet), Set.withoutTiebreak().start(), Game.start());
    }

    private void assertMatchNotOver() {
        if (matchIsOver()) {
            throw new AlreadyTerminatedException("Must not score on terminated Match! Final score is " + printFullScore());
        }
    }

    private int noOfSetsToWin() {
        return this.matchMode == THREE ? 2 : 3;
    }

    private List<Set> addTerminatedSet(final Set terminatedSet) {
        List<Set> updatedTerminatedSets = new ArrayList<>(terminatedSets);
        updatedTerminatedSets.add(terminatedSet);
        return updatedTerminatedSets;
    }

    private String printTerminatedSets() {
        StringBuilder scoreBuilder = new StringBuilder();
        terminatedSets.forEach(set -> scoreBuilder.append(printSet(set)));
        return scoreBuilder.toString();
    }

    private String printSet(final Set set) {
        return String.format("[%s]", set.print());
    }

}
