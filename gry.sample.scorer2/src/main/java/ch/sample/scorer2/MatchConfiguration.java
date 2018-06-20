package ch.sample.scorer2;

import static ch.sample.scorer2.MatchConfiguration.BestOf.THREE;
import static ch.sample.scorer2.MatchConfiguration.Tiebreaks.IN_ALL_SETS;

public class MatchConfiguration {

    enum BestOf {THREE, FIVE}
    enum Tiebreaks {IN_NO_SET, IN_ALL_SETS, IN_ALL_BUT_THE_FINAL_SET}

    private BestOf matchMode = THREE;
    private Tiebreaks tiebreakSetup = IN_ALL_SETS;

    public Match start() {
        return Match.__start(this.matchMode, this.tiebreakSetup);
    }

    public MatchConfiguration bestOf(BestOf bestOf) {
        this.matchMode = bestOf;
        return this;
    }

    public MatchConfiguration withTiebreaks(Tiebreaks tiebreakSetup) {
        this.tiebreakSetup = tiebreakSetup;
        return this;
    }

}
