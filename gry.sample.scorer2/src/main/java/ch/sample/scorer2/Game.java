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
        if(isGameOver()) {
            throw new RuntimeException("must not score on a terminated game!");
        }
        return new Game(nextScore(scoreA), scoreB);
    }

    public Game scoreB() {
        if(isGameOver()) {
            throw new RuntimeException("must not score on a terminated game!");
        }
        return new Game(scoreA, nextScore(scoreB));
    }

    private int nextScore(final int currentScore) {
        return currentScore < 30 ? currentScore + 15 : currentScore + 10;
    }

    private boolean isDeuce() {
        return scoreA == scoreB && scoreA >= 40;
    }

    private boolean isAdvantage() {
        return scoreA != scoreB && scoreA >= 40 && scoreB >= 40;
    }

    private boolean isGameOver() {
        return isTwoRalliesApart() && (scoreA>40 || scoreB>40);
    }

    private boolean isTwoRalliesApart() {
        return Math.abs(scoreA - scoreB) > 15;
    }

    private char getLeader() {
        return scoreA > scoreB ? 'A' : 'B';
    }

    @Override
    public String toString() {
        if(isDeuce()) {
            return "Deuce";
        }
        if(isAdvantage()) {
            return String.format("Advantage %s", getLeader());
        }
        if(isGameOver()) {
            return String.format("Game %s", getLeader());
        }
        return isDeuce() ? "Deuce" : String.format("%d:%d", scoreA, scoreB);
    }

}
