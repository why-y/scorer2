package ch.sample.scorer2;

import java.util.ArrayList;
import java.util.List;

public class MatchScore {

    enum Mode {BEST_OF_3, BEST_OF_5}

    private final Mode mode;
    private final List<Set> terminatedSets;
    private final Set currentSet;
    private final Game currentGame;

    private MatchScore(Mode mode, final List<Set> terminatedSets, Set currentSet, Game currentGame) {
        this.mode = mode;
        this.terminatedSets = terminatedSets;
        this.currentSet = currentSet;
        this.currentGame = currentGame;
    }

    public static MatchScore start() {
        return start(Mode.BEST_OF_3);
    }

    public static MatchScore start(Mode mode) {
        return new MatchScore(mode, new ArrayList<>(mode==Mode.BEST_OF_3 ? 3 : 5), Set.withTiebreak().start(), Game.start());
    }

    public MatchScore scoreA() {
        assertMatchNotOver();
        Game updatedGame = currentGame.scoreA();
        if(updatedGame.isOver()) {
            return updateSetAndStartNewGame(currentSet.scoreA());
        }
        return new MatchScore(mode, terminatedSets, currentSet, updatedGame);
    }

    public MatchScore scoreB() {
        assertMatchNotOver();
        Game updatedGame = currentGame.scoreB();
        if(updatedGame.isOver()) {
            return updateSetAndStartNewGame(currentSet.scoreB());
        }
        return new MatchScore(mode, terminatedSets, currentSet, updatedGame);
    }

    public boolean matchIsOver() {
        return aHasWon() || bHasWon();
    }

    public String printFullScore() {
        return matchIsOver()
                ? String. format("%s - Game, Set and Match Player %s", printTerminatedSets(), aHasWon() ? "A" : "B")
                : String.format("%s%s-%s", printTerminatedSets(), printSet(currentSet), currentGame.print());
    }

    private boolean aHasWon() {
        return terminatedSets.stream().filter(set -> set.aHasWon()).count() >= noOfSetsToWin();
    }

    private boolean bHasWon() {
        return terminatedSets.stream().filter(set -> set.bHasWon()).count() >= noOfSetsToWin();
    }

    private MatchScore updateSetAndStartNewGame(final Set updatedSet) {
        return updatedSet.isOver() ?
                updateTerminatedSetsAndStartNewSet(updatedSet) :
                new MatchScore(mode, terminatedSets, updatedSet, Game.start());
    }

    private MatchScore updateTerminatedSetsAndStartNewSet(final Set terminatedSet) {
        return new MatchScore(mode, addTerminatedSet(terminatedSet), Set.withoutTiebreak().start(), Game.start());
    }

    private void assertMatchNotOver() {
        if (matchIsOver()) {
            throw new AlreadyTerminatedException("Must not score on terminated Match! Final score is " + printFullScore());
        }
    }

    private int noOfSetsToWin() {
        return this.mode==Mode.BEST_OF_3 ? 2 : 3;
    }

    private List<Set> addTerminatedSet(final Set terminatedSet) {
        List<Set> updatedTerminatedSets = new ArrayList<>(terminatedSets);
        updatedTerminatedSets.add(terminatedSet);
        return updatedTerminatedSets;
    }

    private String printTerminatedSets() {
        StringBuilder score = new StringBuilder();
        terminatedSets.forEach(set -> score.append(printSet(set)));
        return score.toString();
    }

    private String printSet(final Set set) {
        return String.format("[%s]", set.print());
    }

}
