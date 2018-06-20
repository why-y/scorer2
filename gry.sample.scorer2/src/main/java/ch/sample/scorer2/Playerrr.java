package ch.sample.scorer2;

public class Playerrr {

    private final String name;

    private Playerrr(final String name) {
        this.name = name;
    }

    public static Playerrr named(final String name) {
        return new Playerrr(name);
    }

    public String printName() {
        return name;
    }
}
