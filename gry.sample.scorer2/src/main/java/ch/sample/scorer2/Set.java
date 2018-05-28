package ch.sample.scorer2;

public class Set {

    private final int scoreA;

    private final int scoreB;

    private Set(int scoreA, int scoreB) {
        this.scoreA = scoreA;
        this.scoreB = scoreB;
    }

    public static Set start() {
        return new Set(0,0);
    }

    public String print() {
        return String.format("%d:%d", scoreA, scoreB);
    }

    public Set scoreA() {
        checkIfNotYetTerminated();
        return new Set(scoreA+1, scoreB);
    }

    public Set scoreB() {
        checkIfNotYetTerminated();
        return new Set(scoreA, scoreB+1);
    }

    public boolean isOver() {
        return oneIsTwoGamesAhead() & scoreA >= 6;
    }

    private boolean oneIsTwoGamesAhead() {
        return Math.abs(scoreA-scoreB) >= 2;
    }

    private void checkIfNotYetTerminated() {
        if(isOver()) throw new AlreadyTerminatedException("Must not score on terminated Set! Final score is " + print());
    }

}
