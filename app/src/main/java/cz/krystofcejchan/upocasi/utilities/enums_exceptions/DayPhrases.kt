package cz.krystofcejchan.upocasi.utilities.enums_exceptions

import java.time.LocalTime

enum class DayPhrases(val time: LocalTime) {
    MORNING(LocalTime.NOON), AFTERNOON(
        LocalTime.of(
            18,
            0
        )
    ),
    EVENING(LocalTime.MAX), DAY(LocalTime.MAX);
}