package cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.forecast.days

import cz.krystofcejchan.upocasi.weather_measurement.IsNumeric
import cz.krystofcejchan.upocasi.weather_measurement.UtilityClass
import cz.krystofcejchan.upocasi.weather_measurement.enums_exception.enums.DAY
import cz.krystofcejchan.upocasi.weather_measurement.enums_exception.enums.TIME
import cz.krystofcejchan.upocasi.weather_measurement.enums_exception.exceptions.NoDataFoundForThisDayAndTime
import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.MethodRefPrint
import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.forecast.WeatherForecast
import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.forecast.days.hour.ForecastAtHour
import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.forecast.days.hour.IForecastDayTimesAndDays
import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.nearest_area.NearestArea
import org.jetbrains.annotations.Contract
import org.json.JSONObject
import java.time.LocalDate
import java.time.LocalTime
import java.util.Arrays
import java.util.EnumMap

/**
 * [WeatherForecast] for today
 *
 * @author krystof-cejchan
 */
class Today(location: String, vararg times: TIME) : IForecastDayTimesAndDays {
    /**
     *
     * @return location provided in the constructor of this class or its parent class <br></br>
     * if you need to get more detailed information, use [cz.krystofcejchan.lite_weather_lib.weather_objects.subparts.nearest_area.NearestArea]
     */
    val location: String
    val moonIllumination: Int
    val moonPhase: String
    val moonRise: LocalTime?
    val moonSet: LocalTime?
    val sunSet: LocalTime?
    val sunRise: LocalTime?
    val averageTemperatureC: Int
    val averageTemperatureF: Int
    val date: LocalDate
    val maxTemperatureC: Int
    val maxTemperatureF: Int
    val minTemperatureC: Int
    val minTemperatureF: Int
    val sunHour: Double
    val totalSnowCM: Double
    val totalSnowInches: Double
    val uvIndex: Int
    val forecastHourlyList: List<ForecastAtHour> = ArrayList()

    /**
     * [TIME]s <br></br>
     * times provided to the constructor<br></br> **duplicate items will be eventually removed -there will be unique TIMEs in the array**
     */
    override val time: Array<TIME>

    init {
        var times = times
        this.location = location
        if (listOf(*times).contains(TIME.ALL)) {
            val t = ArrayList<TIME>()
            for (time in TIME.values()) {
                if (time != TIME.ALL) t.add(time)
            }
            times = t.toTypedArray()
        }
        time = arrayOf<TIME>(*times)
        val jsonObject: JSONObject? = UtilityClass.getJson(location)
        var daily = jsonObject?.getJSONArray("weather")?.getJSONObject(0)?.getJSONArray("astronomy")
            ?.getJSONObject(0)
        moonIllumination = daily!!.getInt("moon_illumination")
        moonPhase = daily.getString("moon_phase")
        moonRise =
            if (IsNumeric.containsNumbers(daily.getString("moonrise"))) UtilityClass.Companion.stringToLocalTime(
                StringBuilder(daily.getString("moonrise"))
            ) else null
        moonSet =
            if (IsNumeric.containsNumbers(daily.getString("moonset"))) UtilityClass.Companion.stringToLocalTime(
                StringBuilder(daily.getString("moonset"))
            ) else null
        sunRise =
            if (IsNumeric.containsNumbers(daily.getString("sunrise"))) UtilityClass.Companion.stringToLocalTime(
                StringBuilder(daily.getString("sunrise"))
            ) else null
        sunSet =
            if (IsNumeric.containsNumbers(daily.getString("sunset"))) UtilityClass.Companion.stringToLocalTime(
                StringBuilder(daily.getString("sunset"))
            ) else null
        daily = jsonObject!!.getJSONArray("weather").getJSONObject(0)
        averageTemperatureC = daily.getInt("avgtempC")
        averageTemperatureF = daily.getInt("avgtempF")
        date = UtilityClass.Companion.stringToDate(StringBuilder(daily.getString("date")))
        maxTemperatureC = daily.getInt("maxtempC")
        maxTemperatureF = daily.getInt("maxtempF")
        minTemperatureC = daily.getInt("mintempC")
        minTemperatureF = daily.getInt("mintempF")
        sunHour = daily.getDouble("sunHour")
        totalSnowCM = daily.getDouble("totalSnow_cm")
        totalSnowInches = totalSnowCM / 2.54
        uvIndex = daily.getInt("uvIndex")
        for (t in times) ForecastAtHour(jsonObject, day, t).let { super.addHour(it) }
        System.gc()
    }

    override val day: DAY
        get() = DAY.TODAY

    @Throws(NoDataFoundForThisDayAndTime::class)
    override fun getForecastByTime(time: TIME): ForecastAtHour {
        return IForecastDayTimesAndDays.getMatchingObjectFrom(day, time)
    }

    /**
     * get all possible forecast for [TIME]s provided in constructor
     *
     * @return map of time and forecast
     */
    val allForecastsForToday: Map<TIME, ForecastAtHour>
        get() {
            val map: MutableMap<TIME, ForecastAtHour> = EnumMap(TIME::class.java)
            for (time in time) {
                try {
                    map[time] = getForecastByTime(time)
                } catch (exception: NoDataFoundForThisDayAndTime) {
                    exception.printStackTrace()
                }
            }
            return map
        }

    /**
     * prints current object.toString to the console
     */
    fun print() {
        MethodRefPrint(this).print()
    }

    @Contract(pure = true)
    override fun toString(): String {
        return """
             ---Today---
             times=${Arrays.toString(time)}
             oonIllumination=$moonIllumination
             moonPhase='$moonPhase'
             moonRise=$moonRise
             moonSet=$moonSet
             sunSet=$sunSet
             sunRise=$sunRise
             averageTemperatureC=$averageTemperatureC
             averageTemperatureF=$averageTemperatureF
             date=$date
             maxTemperatureC=$maxTemperatureC
             maxTemperatureF=$maxTemperatureF
             minTemperatureC=$minTemperatureC
             minTemperatureF=$minTemperatureF
             sunHour=$sunHour
             totalSnowCM=$totalSnowCM
             totalSnowInches=$totalSnowInches
             uvIndex=$uvIndex
             forecastHourlyList=$forecastHourlyList
             """.trimIndent()
    }
}