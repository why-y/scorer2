package ch.sample.scorer2;

import static ch.sample.scorer2.Player.A;
import static ch.sample.scorer2.Player.B;

public class Game implements ScoreUnit {

    private int scoreA;
    private int scoreB;

    private Game(int scoreA, int scoreB) {
        this.scoreA = scoreA;
        this.scoreB = scoreB;
    }

    public static Game start() {
        return new Game(0,0);
    }

    @Override
    public Game score(Player player) {
        assertGameNotOver();
        return player == A ? new Game(getNextScore(scoreA), scoreB) : new Game(scoreA, getNextScore(scoreB));
    }

    @Override
    public String print() {
        if(isOver()) {
            return String.format("Game %s", getLeader());
        }
        else if(isAdvantage()) {
            return String.format("Advantage %s", getLeader());
        }
        else if(isDeuce()) {
            return "Deuce";
        }
        else {
            return String.format("%d:%d", scoreA, scoreB);
        }
    }

    @Override
    public boolean isOver() {
        return isWonBy(A) || isWonBy(B);
    }

    @Override
    public boolean isWonBy(Player player) {
        return isTwoRalliesAhead(player) && getScoreOf(player) > 40 ;
    }

    private int getScoreOf(Player player) {
        return player == A ? scoreA : scoreB;
    }

    private int getNextScore(final int currentScore) {
        return currentScore < 30 ? currentScore + 15 : currentScore + 10;
    }

    private boolean isDeuce() {
        return scoreA == scoreB && scoreA >= 40;
    }

    private boolean isAdvantage() {
        return scoreA != scoreB && scoreA >= 40 && scoreB >= 40;
    }

    private boolean isTwoRalliesAhead(Player player) {
        return player == A ?
                (scoreA - scoreB) > 15 :
                (scoreB - scoreA) > 15;
    }

    private Player getLeader() {
        if(scoreA==scoreB) return null;
        return scoreA > scoreB ? A : B;
    }

    private void assertGameNotOver() {
        if(isOver()) {
            throw new AlreadyTerminatedException("must not score on a terminated game!");
        }
    }


    //////////// for JSON serializing /////////////
    private static final String DEUCE = "DEUCE";
    private static final String ADVANTAGE = "ADVANTAGE";

    public String getScoreA() {
        if(scoreA>=40 && scoreA==scoreB) return DEUCE;
        if(scoreA>40 && scoreA>scoreB) return ADVANTAGE;
        if(scoreB>40) return "";
        return String.valueOf(scoreA);
    }

    public String getScoreB() {
        if(scoreB>=40 && scoreA==scoreB) return DEUCE;
        if(scoreB>40 && scoreB>scoreA) return ADVANTAGE;
        if(scoreA>40) return "";
        return String.valueOf(scoreB);
    }

    public boolean isTiebreak() {
        return false;
    }
    ///////////////////////////////////////////////

}
