package cz.krystofcejchan.upocasi.weather_measurement.enums_exception.enums

import java.util.Arrays
import java.util.stream.Collectors

/**
 * DAYs for weather forecast
 * <br></br> TODAY, TOMORROW, AFTER_TOMORROW, ALL;
 */
enum class DAY {
    /**
     * today
     */
    TODAY,

    /**
     * tomorrow
     */
    TOMORROW,

    /**
     * the day after tomorrow
     */
    AFTER_TOMORROW,

    /**
     * all days
     */
    ALL;

    companion object {
        /**
         * case TODAY -> 0;
         * case TOMORROW -> 1;
         * case AFTER_TOMORROW -> 2;
         * case ALL -> -1;
         *
         * @param day [DAY]
         * @return DAY's index
         */
        fun getIndex(day: DAY?): Int {
            when (day) {
                TODAY -> return 0
                TOMORROW -> return 1
                AFTER_TOMORROW -> return 2
                ALL -> return -1
                else -> {
                    return 0
                }
            }
        }

        /**
         * returns all [DAY] values expect the one included in param
         *
         * @param day TIME to be excluded
         * @return [List] of all [DAY]s except param
         */
        fun getAllDaysExcept(day: DAY): List<DAY> {
            return Arrays.stream(values()).filter { it: DAY -> it != ALL }
                .collect(Collectors.toList())
        }
    }
}