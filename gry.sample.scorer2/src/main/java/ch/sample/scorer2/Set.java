package ch.sample.scorer2;

import static ch.sample.scorer2.Set.*;

public class Set {

    public enum Mode {
        ADVANTAGE, TIEBREAK;
        public Set start() {
            return new Set(0,0, this);
        }
    };

    private final int scoreA;
    private final int scoreB;
    private final Mode mode;

    private Set(int scoreA, int scoreB, Mode mode) {
        this.scoreA = scoreA;
        this.scoreB = scoreB;
        this.mode = mode;
    }

    public static Set start() {
        return withoutTiebreak().start();
    }

    public static Mode withTiebreak() {
        return Mode.TIEBREAK;
    }

    public static Mode withoutTiebreak() {
        return Mode.ADVANTAGE;
    }

    public String print() {
        return String.format("%d:%d", scoreA, scoreB);
    }

    public Set scoreA() {
        mustNotBeTerminated();
        return new Set(scoreA+1, scoreB, mode);
    }

    public Set scoreB() {
        mustNotBeTerminated();
        return new Set(scoreA, scoreB+1, mode);
    }

    public boolean isOver() {
        return aHasWon() || bHasWon();
    }

    private boolean aHasWon() {
        return hasWonByTwoGames(scoreA, scoreB) || hasWonInTiebreak(scoreA, scoreB);
    }

    private boolean bHasWon() {
        return hasWonByTwoGames(scoreB, scoreA) || hasWonInTiebreak(scoreB, scoreA);
    }

    private boolean hasWonInTiebreak(int potentialWinnerScore, int opponentScore) {
        return isaTiebreakSet() && potentialWinnerScore == 6 && opponentScore == 5;
    }

    private boolean hasWonByTwoGames(int potentialWinnerScore, int opponentScore) {
        return potentialWinnerScore >= 6 && potentialWinnerScore-opponentScore >=2;
    }

    private boolean isaTiebreakSet() {
        return mode == Mode.TIEBREAK;
    }

    private void mustNotBeTerminated() {
        if(isOver()) throw new AlreadyTerminatedException("Must not score on terminated Set! Final score is " + print());
    }

}
