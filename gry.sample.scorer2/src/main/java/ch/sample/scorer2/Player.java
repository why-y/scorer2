package ch.sample.scorer2;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Player {
    A, B;

    @JsonValue
    public String getPlayer() {
        return toString();
    }
}
