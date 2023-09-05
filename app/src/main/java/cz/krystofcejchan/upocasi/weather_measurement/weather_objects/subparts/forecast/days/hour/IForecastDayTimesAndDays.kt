package cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.forecast.days.hour

import cz.krystofcejchan.upocasi.weather_measurement.UtilityClass
import cz.krystofcejchan.upocasi.weather_measurement.enums_exception.enums.DAY
import cz.krystofcejchan.upocasi.weather_measurement.enums_exception.enums.TIME
import cz.krystofcejchan.upocasi.weather_measurement.enums_exception.exceptions.CannotSearchForAll
import cz.krystofcejchan.upocasi.weather_measurement.enums_exception.exceptions.NoDataFoundForThisDayAndTime
import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.forecast.days.AfterTomorrow
import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.forecast.days.Today
import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.forecast.days.Tomorrow
import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.forecast.days.hour.ForecastAtHour

/**
 * inteface impletended in [cz.krystofcejchan.lite_weather_lib.weather_objects.subparts.forecast.days.Today]
 * [cz.krystofcejchan.lite_weather_lib.weather_objects.subparts.forecast.days.Tomorrow] and [cz.krystofcejchan.lite_weather_lib.weather_objects.subparts.forecast.days.AfterTomorrow]
 *
 * @author krystof-cejchan
 */
interface IForecastDayTimesAndDays {
    /**
     * @return day which implementing class represents
     */
    val day: DAY

    /**
     * @return time provided by user when calling this class or superclass
     */
    val time: Array<TIME>

    /**
     * implemented by Today, Tomorrow and AfterTomorrow, therefor no day is needed
     *
     * @param time for weather data
     * @return ForecastAtHour object containing all data for specific day and time
     */
    @Throws(NoDataFoundForThisDayAndTime::class)
    fun getForecastByTime(time: TIME): ForecastAtHour

    /**
     * add Forecast object
     *
     * @param forecast [cz.krystofcejchan.lite_weather_lib.weather_objects.subparts.forecast.days.hour.ForecastAtHour] object containing weather data for specific day and time(hour)
     */
    fun addHour(forecast: ForecastAtHour) {
        val forecastHourlyList = UtilityClass.Storage.listOfAllDaysAndItsTimes
        if (forecastHourlyList.stream().noneMatch { it: ForecastAtHour? -> it == forecast }) {
            forecastHourlyList.add(forecast)
            UtilityClass.Storage.addToListOfAllDaysAndItsTimes(forecast)
        }
    }

    companion object {
        /**
         * returns forecast for specific day and hour
         *
         * @param day  for which you need the forecast
         * @param time for which you need the forecast
         * @return Forecast data for provided [DAY] and [TIME]; if not found [NoDataFoundForThisDayAndTime] exception is thrown
         * @throws NoDataFoundForThisDayAndTime if no forecast was included for this [TIME] and [DAY];
         * you need to use TIME.ALL with DAY.ALL if you want to prevent this
         * or include [TIME] and [DAY] you want to know the forecast for in the constructor when creating forecast object or its subclasses
         * @throws CannotSearchForAll           [DAY] or [TIME] is set to ALL; you can get single object of [cz.krystofcejchan.lite_weather_lib.weather_objects.subparts.forecast.days.hour.ForecastAtHour] only for certain DAY and TIME
         */
        @Throws(NoDataFoundForThisDayAndTime::class, CannotSearchForAll::class)
        fun getMatchingObjectFrom(day: DAY, time: TIME): ForecastAtHour {
            if (day == DAY.ALL || time == TIME.ALL) throw CannotSearchForAll("In order to get Forecast for certain hour, you need to pass any DAY or TIME except ALL")
            return UtilityClass.Storage.listOfAllDaysAndItsTimes.stream()
                .filter { f: ForecastAtHour -> f.day == day && f.time == time }
                .findFirst()
                .orElseThrow { NoDataFoundForThisDayAndTime("No data found for such day and time") }
        }

        fun clearSavedForecasts() {
            UtilityClass.Storage.clearList()
        }

        fun removedSavedForecast(forecast: ForecastAtHour) {
            UtilityClass.Storage.removeElement(forecast)
        }
    }
}