package cz.krystofcejchan.upocasi.utilities.enums_exceptions;

import java.time.LocalTime;

public enum DayPhrases {

    MORNING(LocalTime.NOON),
    AFTERNOON(LocalTime.of(18, 0)),
    EVENING(LocalTime.MAX),
    DAY(LocalTime.MAX);

    public final LocalTime time;

    DayPhrases(LocalTime time) {
        this.time = time;
    }
}
