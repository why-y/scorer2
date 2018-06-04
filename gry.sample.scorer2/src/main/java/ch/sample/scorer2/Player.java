package ch.sample.scorer2;

public class Player {

    private final String name;

    private Player(final String name) {
        this.name = name;
    }

    public static Player named(final String name) {
        return new Player(name);
    }

    public String printName() {
        return name;
    }
}
