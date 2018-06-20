package ch.sample.scorer2;

public interface ScoreUnit {
    ScoreUnit scoreA();
    ScoreUnit scoreB();
    boolean isOver();
    String print();
}
