package ch.sample.scorer2;

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
    public Tiebreak scoreA() {
        return new Tiebreak(scoreA+1, scoreB);
    }

    @Override
    public Tiebreak scoreB() {
        return new Tiebreak(scoreA, scoreB+1);
    }

    @Override
    public boolean isOver() {
        return (scoreA >= 7 || scoreB >= 7) && oneIsTwoRalliesAhead();
    }

    @Override
    public String print() {
        return String.format("(%d:%d)", scoreA, scoreB);
    }

    private boolean oneIsTwoRalliesAhead() {
        return Math.abs(scoreA-scoreB) >= 2;
    }

}