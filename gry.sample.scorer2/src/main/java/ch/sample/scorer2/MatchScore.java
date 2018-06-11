package ch.sample.scorer2;

import java.util.ArrayList;
import java.util.List;

public class MatchScore {

    private final List<Set> terminatedSets;
    private final Set currentSet;
    private final Game currentGame;

    private MatchScore(final List<Set> terminatedSets, Set currentSet, Game currentGame) {
        this.terminatedSets = terminatedSets;
        this.currentSet = currentSet;
        this.currentGame = currentGame;
    }

    public static MatchScore start() {
        return new MatchScore(new ArrayList<>(5), Set.withTiebreak().start(), Game.start());
    }

    public MatchScore scoreA() {
        if (matchIsOver()) {
            throw new AlreadyTerminatedException("Must not score on terminated Match! Final score is " + printFullScore());
        }
        Game updatedGame = currentGame.scoreA();
        if(updatedGame.isOver()){
            Game newGame = Game.start();
            Set updatedSet = currentSet.scoreA();
            if(updatedSet.isOver()) {
                List<Set> updatedSets = addTerminatedSet(updatedSet);
                Set newSet = Set.withoutTiebreak().start();
                return new MatchScore(updatedSets, newSet, newGame);
            }
            return new MatchScore(terminatedSets, updatedSet, newGame);
        }
        return new MatchScore(terminatedSets, currentSet, currentGame.scoreA());
    }

    public boolean matchIsOver() {
        return aHasWon() || bHasWon();
    }

    private boolean aHasWon() {
        return terminatedSets.stream().filter(set -> set.aHasWon()).count() >= 2;
    }

    private boolean bHasWon() {
        return terminatedSets.stream().filter(set -> set.bHasWon()).count() >= 2;
    }

    private List<Set> addTerminatedSet(final Set terminatedSet) {
        List<Set> updatedTerminatedSets = new ArrayList<>(terminatedSets);
        updatedTerminatedSets.add(terminatedSet);
        return updatedTerminatedSets;
    }

    public String printFullScore() {
        return matchIsOver()
                ? String. format("%s - Game, Set and Match Player %s", printTerminatedSets(), aHasWon() ? "A" : "B")
                : String.format("%s%s-%s", printTerminatedSets(), printSet(currentSet), currentGame.print());
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
