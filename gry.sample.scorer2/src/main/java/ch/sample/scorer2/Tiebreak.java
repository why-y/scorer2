package ch.sample.scorer2;

import static ch.sample.scorer2.Player.*;

public class Tiebreak implements ScoreUnit {
    private final int scoreA;
    private final int scoreB;

    private Tiebreak(int scoreA, int scoreB) {
        this.scoreA = scoreA;
        this.scoreB = scoreB;
    }

    public static Tiebreak start () {
        return new Tiebreak(0,0);
    }

    @Override
    public Tiebreak score(Player player) {
        return player == A ? new Tiebreak(scoreA+1, scoreB) : new Tiebreak(scoreA, scoreB+1);
    }

    @Override
    public boolean isOver() {
        return isWonBy(A) || isWonBy(B);
    }

    @Override
    public boolean isWonBy(Player player) {
        return isTwoRalliesAhead(player) && (player == A ? scoreA >=7 : scoreB >=7);
    }

    @Override
    public String print() {
        return String.format("(%d:%d)", scoreA, scoreB);
    }

    //////////// for JSON serializing /////////////
    public String getScoreA() {
        return String.valueOf(scoreA);
    }

    public String getScoreB() {
        return String.valueOf(scoreB);
    }

    public boolean isTiebreak() {
        return true;
    }

    ///////////////////////////////////////////////

    private boolean isTwoRalliesAhead(Player player) {
        return player == A ?
                (scoreA - scoreB) >= 2 :
                (scoreB - scoreA) >= 2;
    }

}
