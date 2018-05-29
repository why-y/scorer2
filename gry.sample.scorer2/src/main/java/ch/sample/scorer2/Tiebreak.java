package ch.sample.scorer2;

public class Tiebreak {
    private final int scoreA;
    private final int scoreB;

    private Tiebreak(int scoreA, int scoreB) {
        this.scoreA = scoreA;
        this.scoreB = scoreB;
    }

    public static Tiebreak start () {
        return new Tiebreak(0,0);
    }

    public Tiebreak scoreA() {
        return new Tiebreak(scoreA+1, scoreB);
    }

    public Tiebreak scoreB() {
        return new Tiebreak(scoreA, scoreB+1);
    }

    public boolean isOver() {
        return (scoreA >= 7 || scoreB >= 7) && oneIsTwoRalliesAhead();
    }

    private boolean oneIsTwoRalliesAhead() {
        return Math.abs(scoreA-scoreB) >= 2;
    }

    public String print() {
        return String.format("%d:%d", scoreA, scoreB);
    }
}
