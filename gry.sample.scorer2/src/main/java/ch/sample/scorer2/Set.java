package ch.sample.scorer2;

import java.util.ArrayList;
import java.util.List;

public class Set implements ScoreUnit{

    public enum Mode {
        ADVANTAGE, TIEBREAK;
        public Set start() {
            return new Set(this, new ArrayList<ScoreUnit>(), Game.start());
        }
    };

    private final Mode mode;
    private final List<ScoreUnit> terminatedScoreUnits;
    private final ScoreUnit currentScoreUnit;

    private Set(Mode mode, final List<ScoreUnit> terminatedScoreUnits, ScoreUnit currentScoreUnit) {
        this.mode = mode;
        this.terminatedScoreUnits = terminatedScoreUnits;
        this.currentScoreUnit = currentScoreUnit;
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
        ScoreUnit updatedScoreUnit = currentScoreUnit.score(player);
        if(updatedScoreUnit.isOver()) {
            terminatedScoreUnits.add(updatedScoreUnit);
            return new Set(this.mode, this.terminatedScoreUnits, startNextScoreUnit());
        }
        else {
            return new Set(this.mode, this.terminatedScoreUnits, updatedScoreUnit);
        }
    }

    @Override
    public String print() {
        return isOver() ?
                String.format("[%s]", printTerminatedGames()) :
                String.format("%s %s", printTerminatedGames(), printCurrentScoreUnit());
    }

    @Override
    public boolean isOver() {
        return isWonBy(Player.A) || isWonBy(Player.B);
    }

    @Override
    public boolean isWonBy(Player player) {
        Player opponent = player == Player.A ? Player.B : Player.A;
        return hasWonByTwoGames(noOfGamesWonBy(player), noOfGamesWonBy(opponent)) || hasWonInTiebreak(noOfGamesWonBy(player), noOfGamesWonBy(opponent));
    }

    boolean requiresTiebreak() {
        return isaTiebreakSet() && noOfGamesWonBy(Player.A) == 6 && noOfGamesWonBy(Player.B) == 6;
    }

    private ScoreUnit startNextScoreUnit() {
        return requiresTiebreak() ? Tiebreak.start() : Game.start();
    }

    private String printTerminatedGames() {
        return String.format("%d:%d", noOfGamesWonBy(Player.A), noOfGamesWonBy(Player.B));
    }

    private String printCurrentScoreUnit() {
        return isOver() ? "" : currentScoreUnit.print();
    }

    private long noOfGamesWonBy(Player player) {
        return terminatedScoreUnits.stream().filter(su -> su.isWonBy(player)).count();
    }

    private boolean hasWonInTiebreak(long potentialWinnerScore, long opponentScore) {
        return isaTiebreakSet() && potentialWinnerScore == 7 && opponentScore == 6;
    }

    private boolean hasWonByTwoGames(long potentialWinnerScore, long opponentScore) {
        return potentialWinnerScore >= 6 && potentialWinnerScore-opponentScore >=2;
    }

    private boolean isaTiebreakSet() {
        return mode == Mode.TIEBREAK;
    }

    private void mustNotBeTerminated() {
        if(isOver()) throw new AlreadyTerminatedException("Must not score on terminated Set! Final score is " + print());
    }

}
