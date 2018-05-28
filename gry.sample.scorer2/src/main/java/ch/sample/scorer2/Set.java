package ch.sample.scorer2;

public class Set {

    public enum Mode {PLAY_OFF, TIE_BREAK};

    private final int scoreA;
    private final int scoreB;
    private final Mode mode;


    private Set(int scoreA, int scoreB) {
        this(scoreA, scoreB, Mode.PLAY_OFF);
    }

    private Set(int scoreA, int scoreB, Mode mode) {
        this.scoreA = scoreA;
        this.scoreB = scoreB;
        this.mode = mode;
    }

    public static Set start() {
        return new Set(0,0);
    }

    public String print() {
        return String.format("%d:%d", scoreA, scoreB);
    }

    public Set scoreA() {
        mustNotBeTerminated();
        return new Set(scoreA+1, scoreB);
    }

    public Set scoreB() {
        mustNotBeTerminated();
        return new Set(scoreA, scoreB+1);
    }

    public boolean isOver() {
        return oneIsTwoGamesAhead() & scoreA >= 6;
    }

    private boolean oneIsTwoGamesAhead() {
        return Math.abs(scoreA-scoreB) >= 2;
    }

    private void mustNotBeTerminated() {
        if(isOver()) throw new AlreadyTerminatedException("Must not score on terminated Set! Final score is " + print());
    }

}
