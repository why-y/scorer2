package ch.sample.scorer2;

import java.util.ArrayList;
import java.util.List;

import ch.sample.scorer2.MatchConfiguration.BestOf;
import ch.sample.scorer2.MatchConfiguration.Tiebreaks;
import static ch.sample.scorer2.MatchConfiguration.BestOf.*;
import static ch.sample.scorer2.MatchConfiguration.Tiebreaks.*;
import static ch.sample.scorer2.Player.*;

public class Match implements ScoreUnit {

    private static final BestOf DEFAUL_MODE = THREE;
    private static final Tiebreaks DEFAULT_TIEBREAKS = IN_ALL_SETS;

    private final BestOf matchMode;
    private final Tiebreaks tiebreakMode;
    private final List<Set> terminatedSets;
    private final Set currentSet;

    private Match(BestOf matchMode, Tiebreaks tiebreakMode, final List<Set> terminatedSets, Set currentSet) {
        this.matchMode = matchMode;
        this.tiebreakMode = tiebreakMode;
        this.terminatedSets = terminatedSets;
        this.currentSet = currentSet;
    }

    static Match __start(BestOf matchMode, Tiebreaks tiebreakMode) {
        Set startSet = tiebreakMode == Tiebreaks.IN_NO_SET ? Set.withoutTiebreak().start() : Set.withTiebreak().start();
        return new Match(matchMode, tiebreakMode, new ArrayList<>(matchMode == THREE ? 3 : 5), startSet);
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

    @Override
    public Match score(Player player) {
        assertMatchNotOver();
        Set updatedSet = currentSet.score(player);
        if(updatedSet.isOver()) {
            terminatedSets.add(updatedSet);
            return new Match(matchMode, tiebreakMode, terminatedSets, startNextSet());
        }
        else {
            return new Match(matchMode, tiebreakMode, terminatedSets, updatedSet);
        }
    }

    @Override
    public boolean isOver() {
        return isWonBy(A) || isWonBy(B);
    }

    @Override
    public String print() {
        return isOver()
                ? String. format("%s - Game, Set and Match Player %s", printTerminatedSets(), isWonBy(A) ? "A" : "B")
                : String.format("%s%s", printTerminatedSets(), currentSet.print());
    }

    @Override
    public boolean isWonBy(Player player) {
        return noOfSetsWonBy(player) >= noOfSetsToWin();
    }

    public boolean isBestOf(BestOf matchMode) {
        return this.matchMode == matchMode;
    }

    private Set startNextSet() {
        return (tiebreakMode == Tiebreaks.IN_NO_SET || (tiebreakMode == Tiebreaks.IN_ALL_BUT_THE_FINAL_SET && isFinalSet())) ?
                Set.withoutTiebreak().start() : Set.withTiebreak().start();
    }

    private boolean isFinalSet() {
        return matchMode == BestOf.THREE ? noOfTerminatedSets() == 2 : noOfTerminatedSets() == 4;
    }

    private int noOfTerminatedSets() {
        return terminatedSets.size();
    }


    private void assertMatchNotOver() {
        if (isOver()) {
            throw new AlreadyTerminatedException("Must not score on terminated Match! Final score is " + print());
        }
    }

    private int noOfSetsToWin() {
        return this.matchMode == THREE ? 2 : 3;
    }

    private long noOfSetsWonBy(Player player) {
        return terminatedSets.stream().filter(set -> set.isWonBy(player)).count();
    }

    private String printTerminatedSets() {
        StringBuilder scoreBuilder = new StringBuilder();
        terminatedSets.forEach(set -> scoreBuilder.append(set.print()));
        return scoreBuilder.toString();
    }

}
