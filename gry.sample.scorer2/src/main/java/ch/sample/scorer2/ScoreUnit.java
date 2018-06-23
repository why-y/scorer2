package ch.sample.scorer2;

public interface ScoreUnit {
    ScoreUnit score(Player player);
    boolean isOver();
    boolean isWonBy(Player player);
    String print();
}
