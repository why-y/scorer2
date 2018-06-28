package ch.sample.scorer2;

import java.util.Stack;
import java.util.stream.Stream;

public class RalliesStore {

    private Stack<Rally> rallyHistory;

    private RalliesStore() {
        rallyHistory = new Stack<>();
    }

    public static RalliesStore start() {
        return new RalliesStore();
    }

    public RalliesStore add(Rally rally) {
        rallyHistory.push(rally);
        return this;
    }

    public int noOfRallies() {
        return rallyHistory.size();
    }

    public Stream<Rally> streamRallySequence() {
        return rallyHistory.stream();
    }

    public Match applyToMatch(Match match) {
        for (Rally rally: rallyHistory) {
            match = match.score(rally.getWinner());
        }
        return match;
    }

}
