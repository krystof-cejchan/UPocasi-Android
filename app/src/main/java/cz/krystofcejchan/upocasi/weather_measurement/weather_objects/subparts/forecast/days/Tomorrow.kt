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
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.LocalTime
import java.util.Arrays

/**
 * [WeatherForecast] for tommorow
 *
 * @author krystof-cejchan
 */
class Tomorrow(location: String, vararg times: TIME) : IForecastDayTimesAndDays {
    /**
     * [TIME]s <br></br>
     * times provided to the constructor<br></br> **duplicate items will be eventually removed -there will be unique TIMEs in the array**
     */
    override val time: Array<TIME>
        get() = field

    /**
     *
     * @return location provided in the constructor of this class or its parent class <br></br>
     * if you need to get more detailed information, use [NearestArea]
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

    init {
        var times = times
        this.location = location
        if (Arrays.asList<TIME>(*times).contains(TIME.ALL)) {
            times = Arrays.stream<TIME>(TIME.values()).filter { time: TIME -> time != TIME.ALL }
                .toArray<TIME> { emptyArray<TIME>() }
        }
        time = arrayOf(*times)
        val json: JSONObject = UtilityClass.getJson(location)!!
        var daily =
            json.getJSONArray("weather").getJSONObject(0).getJSONArray("astronomy").getJSONObject(0)
        moonIllumination = daily.getInt("moon_illumination")
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
        daily = json.getJSONArray("weather").getJSONObject(0)
        averageTemperatureC = daily.getInt("avgtempC")
        averageTemperatureF = daily.getInt("avgtempF")
        date = UtilityClass.Companion.stringToDate(StringBuilder(daily.getString("date")))
        maxTemperatureC = daily.getInt("maxtempC")
        maxTemperatureF = daily.getInt("maxtempF")
        minTemperatureC = daily.getInt("mintempC")
        minTemperatureF = daily.getInt("mintempF")
        sunHour = daily.getDouble("sunHour")
        totalSnowCM = daily.getDouble("totalSnow_cm")
        totalSnowInches =
            BigDecimal.valueOf(totalSnowCM / 2.54).setScale(2, RoundingMode.UP).toDouble()
        uvIndex = daily.getInt("uvIndex")
        for (t in time) super.addHour(ForecastAtHour(json, day, t))
        System.gc()
    }

    override val day: DAY
        get() = DAY.TOMORROW

    @Throws(NoDataFoundForThisDayAndTime::class)
    override fun getForecastByTime(time: TIME): ForecastAtHour {
        return IForecastDayTimesAndDays.Companion.getMatchingObjectFrom(day, time)
    }

    /**
     * get all possible forecast for [TIME]s provided in constructor
     *
     * @return map of time and forecast
     */
    val allForecastsForToday: Map<TIME, ForecastAtHour>
        get() {
            val map: MutableMap<TIME, ForecastAtHour> = HashMap()
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
               ---Tomorrow---
               times=${Arrays.toString(time)}
               moonIllumination=$moonIllumination
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
               """.trimIndent()
    }
}