package ch.sample.scorer2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Match {

    private static final int MAX_SETS = 5;
    private final String terminatedSetScore;
    private final Set currentSet;
    private final Game currentGame;

    private Match(final String terminatedSetScore, Set currentSet, Game currentGame) {
        this.terminatedSetScore = terminatedSetScore;
        this.currentSet = currentSet;
        this.currentGame = currentGame;
    }

    public static Match start() {
        return new Match("", Set.withTiebreak().start(), Game.start());
    }

    public Match scoreA() {

        if(currentGame.isGameOver()) {
            return new Match(terminatedSetScore, currentSet.scoreA(), Game.start());
        }
        if(currentGame.scoreA().isGameOver()){
            if(currentSet.scoreA().isOver()) {
                return new Match(String.format("%s[%s]", terminatedSetScore, currentSet.scoreA().print()), Set.withTiebreak().start(), Game.start());
            }
            return new Match(terminatedSetScore, currentSet.scoreA(), Game.start());
        }
        return new Match(terminatedSetScore, currentSet, currentGame.scoreA());
    }

    public String print() {
        return String.format("%s-%s", printSets(), currentGame.print());
    }

    private String printSets() {
        return String.format("%s[%s]", terminatedSetScore, currentSet.print());
    }

}
