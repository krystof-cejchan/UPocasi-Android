package cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.forecast

import cz.krystofcejchan.upocasi.weather_measurement.UtilityClass
import cz.krystofcejchan.upocasi.weather_measurement.enums_exception.enums.DAY
import cz.krystofcejchan.upocasi.weather_measurement.enums_exception.enums.TIME
import cz.krystofcejchan.upocasi.weather_measurement.enums_exception.exceptions.NoDataFoundForThisDay
import cz.krystofcejchan.upocasi.weather_measurement.enums_exception.exceptions.NoDataFoundForThisDayAndTime
import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.MethodRefPrint
import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.forecast.days.AfterTomorrow
import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.forecast.days.Today
import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.forecast.days.Tomorrow
import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.forecast.days.hour.ForecastAtHour
import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.forecast.days.hour.IForecastDayTimesAndDays
import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.nearest_area.NearestArea
import java.util.Arrays
import java.util.EnumMap
import java.util.stream.Collectors

/**
 * Weather forecast for today, tomorrow, the day after tomorrow <br></br>
 * Data can be separated into hours → 12am, 3am, 6am, 9am, 12pm, 3pm, 6pm, 9pm.<br></br>
 * see [TIME], [DAY]<br></br>
 *
 * @author krystof-cejchan
 */
class WeatherForecast(
    /**
     * @return location provided in the constructor of this class or its parent class <br></br>
     * if you need to get more detailed information, use [NearestArea]
     */
    val location: String, times: Array<TIME>, vararg days: DAY
) {
    private val today: Today?
    private val tomorrow: Tomorrow?
    private val tomorrowAfter: AfterTomorrow?

    /**
     * @return DAYs provided in constructor
     */
    val days: Array<DAY>

    /**
     * @return TIMEs provided in constructor
     */
    val times: Array<TIME>

    constructor(location: String, time: TIME, vararg days: DAY) : this(
        location,
        arrayOf<TIME>(time),
        *days
    ) {
    }

    constructor(location: String, day: DAY, vararg times: TIME) : this(
        location,
        arrayOf<TIME>(*times),
        day
    ) {
    }

    constructor(location: String, day: Array<DAY>, vararg times: TIME) : this(
        location,
        arrayOf<TIME>(*times),
        *day
    ) {
    }

    init {
        var todayHelper: Today? = null
        var tomorrowHelper: Tomorrow? = null
        var tomorrowAfterHelper: AfterTomorrow? = null

        //filtering the arraylists
        var timeList =
            ArrayList(Arrays.stream(times).collect(Collectors.toList())).stream().distinct()
                .collect(Collectors.toList())
        var dayList =
            ArrayList(Arrays.stream(days).collect(Collectors.toList())).stream().distinct()
                .collect(Collectors.toList())
        if (dayList.contains(DAY.ALL)) dayList =
            Arrays.stream(DAY.values()).collect(Collectors.toList()).stream()
                .filter { day: DAY -> day != DAY.ALL }.collect(Collectors.toList())
        if (timeList.contains(TIME.ALL)) timeList =
            Arrays.stream(TIME.values()).collect(Collectors.toList()).stream()
                .filter { time: TIME -> time != TIME.ALL }.collect(Collectors.toList())
        val timesBackToArray = timeList.toTypedArray()
        this.times = timesBackToArray
        this.days = dayList.toTypedArray()
        for (day in dayList) {
            when (day) {
                DAY.TODAY -> todayHelper = Today(location, *timesBackToArray)
                DAY.TOMORROW -> tomorrowHelper = Tomorrow(location, *timesBackToArray)
                DAY.AFTER_TOMORROW -> tomorrowAfterHelper =
                    AfterTomorrow(location, *timesBackToArray)

                DAY.ALL -> {
                    todayHelper = Today(location, *timesBackToArray)
                    tomorrowHelper = Tomorrow(location, *timesBackToArray)
                    tomorrowAfterHelper = AfterTomorrow(location, *timesBackToArray)
                }
            }
        }
        today = todayHelper
        tomorrow = tomorrowHelper
        tomorrowAfter = tomorrowAfterHelper
    }

    /**
     * returns forecast for specific day and time
     *
     * @param day  [DAY] for which you want to know the forecast
     * @param time [TIME] for which you want to know the forecast
     * @return ForecastAtHour for provided DAY and TIME
     * @throws NoDataFoundForThisDayAndTime if you did not include day and time when creating constructor
     */
    @Throws(NoDataFoundForThisDayAndTime::class)
    fun getForecastFor(day: DAY, time: TIME): ForecastAtHour {
        return IForecastDayTimesAndDays.Companion.getMatchingObjectFrom(day, time)
    }

    /**
     * get all possible forecasts for all days and times you provided in the constructor
     *
     * @return a map of [DAY] and map of [TIME] and [ForecastAtHour]
     */
    val allForecastForAllDayAndAllTime: Map<DAY, Map<TIME, ForecastAtHour>>
        get() {
            val returnMap: MutableMap<DAY, Map<TIME, ForecastAtHour>> = EnumMap(DAY::class.java)
            for (day in days) {
                when (day) {
                    DAY.TODAY -> returnMap[day] = getToday().allForecastsForToday
                    DAY.TOMORROW -> returnMap[day] = getTomorrow().allForecastsForToday
                    DAY.AFTER_TOMORROW -> returnMap[day] = getTomorrowAfter().allForecastsForToday
                    DAY.ALL -> {}
                }
            }
            return returnMap
        }

    /**
     * @return object containing all weather data for today
     * @throws NoDataFoundForThisDay if you did not include this day in constructor
     */
    @Throws(NoDataFoundForThisDay::class)
    fun getToday(): Today {
        if (today == null) throw NoDataFoundForThisDay("Today was not included in the constructor")
        return today
    }

    /**
     * @return object containing all weather data for tomorrow
     * @throws NoDataFoundForThisDay if you did not include this day in constructor
     */
    @Throws(NoDataFoundForThisDay::class)
    fun getTomorrow(): Tomorrow {
        if (tomorrow == null) throw NoDataFoundForThisDay("Tomorrow was not included in the constructor")
        return tomorrow
    }

    /**
     * @return object containing all weather data for the day after tomorrow
     * @throws NoDataFoundForThisDay if you did not include this day in constructor
     */
    @Throws(NoDataFoundForThisDay::class)
    fun getTomorrowAfter(): AfterTomorrow {
        if (tomorrowAfter == null) throw NoDataFoundForThisDay("The day after tomorrow was not included in the constructor")
        return tomorrowAfter
    }

    /**
     * @return all saved forecasts
     */
    val allSavedForecasts: List<ForecastAtHour?>?
        get() = ArrayList(UtilityClass.Storage.listOfAllDaysAndItsTimes)

    /**
     * prints current object.toString to the console
     */
    fun print() {
        MethodRefPrint(this).print()
    }

    /**
     * @return object to string, modified to prevent [NullPointerException] if class fields are null
     */
    override fun toString(): String {
        return """
             ---  WeatherForecast  ---
             --today=
             ${today?.toString() ?: " null "}
             --tomorrow=
             ${tomorrow?.toString() ?: " null "}
             --AfterTomorrow=
             ${tomorrowAfter?.toString() ?: " null "}
             """.trimIndent()
    }

    companion object {
        /**
         * removes saved forecasts
         */
        fun clearSavedForecasts() {
            IForecastDayTimesAndDays.clearSavedForecasts()
        }

        /**
         * removes forecast from param
         *
         * @param forecast to be removed
         */
        fun removedSavedForecast(forecast: ForecastAtHour) {
            IForecastDayTimesAndDays.removedSavedForecast(forecast)
        }
    }
}