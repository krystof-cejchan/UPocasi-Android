package cz.krystofcejchan.upocasi.weather_measurement.enums_exception.enums

import java.time.LocalTime
import java.util.Arrays
import java.util.stream.Collectors

/**
 * TIMES for weather forecast
 * <br></br>
 * AM_3, AM_6, AM_9, AM_12, PM_3, PM_6, PM_9, PM_12, ALL;
 */
enum class TIME(val time: LocalTime) {
    /**
     * 3:00 a.m.
     */
    AM_3(LocalTime.of(3, 0)),

    /**
     * 6:00 a.m.
     */
    AM_6(LocalTime.of(6, 0)),

    /**
     * 9:00 a.m.
     */
    AM_9(LocalTime.of(9, 0)),

    /**
     * noon
     */
    AM_12(LocalTime.NOON),

    /**
     * 3:00 p.m.
     */
    PM_3(LocalTime.of(15, 0)),

    /**
     * 6:00 p.m.
     */
    PM_6(LocalTime.of(18, 0)),

    /**
     * 9:00 p.m.
     */
    PM_9(LocalTime.of(21, 0)),

    /**
     * midnight
     */
    PM_12(LocalTime.MIDNIGHT),

    /**
     * all times
     */
    ALL(LocalTime.MIN);

    companion object {
        /**
         * case PM_12 -> 0;
         * case AM_3 -> 1;
         * case AM_6 -> 2;
         * case AM_9 -> 3;
         * case AM_12 -> 4;
         * case PM_3 -> 5;
         * case PM_6 -> 6;
         * case PM_9 -> 7;
         * case ALL -> -1;
         *
         * @param time [TIME]
         * @return its index
         */
        fun getIndex(time: TIME?): Int {
            return when (time) {
                PM_12 -> 0
                AM_3 -> 1
                AM_6 -> 2
                AM_9 -> 3
                AM_12 -> 4
                PM_3 -> 5
                PM_6 -> 6
                PM_9 -> 7
                ALL -> -1
                else -> -1;
            }
        }

        /**
         * returns all [TIME] values expect the one included in param
         *
         * @param time TIME to be excluded
         * @return [java.util.List] of all [TIME]s except param
         */
        fun getAllEnumsExcept(time: TIME): List<TIME> {
            return Arrays.stream(values()).filter { it: TIME -> it != time }
                .collect(Collectors.toList())
        }
    }
}