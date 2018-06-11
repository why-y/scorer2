package ch.sample.scorer2;

public class Game {

    private final int scoreA;
    private final int scoreB;

    private Game(final int scoreA, final int scoreB) {
        this.scoreA = scoreA;
        this.scoreB = scoreB;
    }

    public static Game start() {
        return new Game(0,0);
    }

    public Game scoreA() {
        assertGameNotOver();
        return new Game(getNextScore(scoreA), scoreB);
    }

    public Game scoreB() {
        assertGameNotOver();
        return new Game(scoreA, getNextScore(scoreB));
    }

    public String print() {
        if(isDeuce()) {
            return "Deuce";
        }
        if(isAdvantage()) {
            return String.format("Advantage %s", getLeader());
        }
        if(isOver()) {
            return String.format("Game %s", getLeader());
        }
        return isDeuce() ? "Deuce" : String.format("%d:%d", scoreA, scoreB);
    }

    boolean isOver() {
        return isTwoRalliesApart() && (scoreA>40 || scoreB>40);
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

    private boolean isTwoRalliesApart() {
        return Math.abs(scoreA - scoreB) > 15;
    }

    private char getLeader() {
        if(scoreA==scoreB) return '-';
        return scoreA > scoreB ? 'A' : 'B';
    }

    private void assertGameNotOver() {
        if(isOver()) {
            throw new AlreadyTerminatedException("must not score on a terminated game!");
        }
    }

}
