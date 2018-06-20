package ch.sample.scorer2;

public class Set implements ScoreUnit{

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
        return withTiebreak().start();
    }

    public static Mode withTiebreak() {
        return Mode.TIEBREAK;
    }

    public static Mode withoutTiebreak() {
        return Mode.ADVANTAGE;
    }

    @Override
    public Set score(Player player) {
        mustNotBeTerminated();
        return player == Player.A ? new Set(scoreA+1, scoreB, mode) : new Set(scoreA, scoreB+1, mode);
    }

    public String print() {
        return String.format("%d:%d", scoreA, scoreB);
    }

    public boolean isOver() {
        return aHasWon() || bHasWon();
    }

    public boolean aHasWon() {
        return hasWonByTwoGames(scoreA, scoreB) || hasWonInTiebreak(scoreA, scoreB);
    }

    public boolean bHasWon() {
        return hasWonByTwoGames(scoreB, scoreA) || hasWonInTiebreak(scoreB, scoreA);
    }

    private boolean hasWonInTiebreak(int potentialWinnerScore, int opponentScore) {
        return isaTiebreakSet() && potentialWinnerScore == 7 && opponentScore == 6;
    }

    private boolean hasWonByTwoGames(int potentialWinnerScore, int opponentScore) {
        return potentialWinnerScore >= 6 && potentialWinnerScore-opponentScore >=2;
    }

    public boolean requiresTiebreak() {
        return isaTiebreakSet() && scoreA == 6 && scoreB == 6;
    }

    private boolean isaTiebreakSet() {
        return mode == Mode.TIEBREAK;
    }

    private void mustNotBeTerminated() {
        if(isOver()) throw new AlreadyTerminatedException("Must not score on terminated Set! Final score is " + print());
    }

}
