package ch.sample.scorer2;

public class Rally {

    private Player winner;

    private Rally(Player winner) {
        this.winner = winner;
    }

    public static Rally wonBy(Player winner) {
        return new Rally(winner);
    }

    public Player getWinner() {
        return winner;
    }
}
