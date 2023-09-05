package cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.forecast.days.hour

import cz.krystofcejchan.upocasi.weather_measurement.enums_exception.enums.DAY
import cz.krystofcejchan.upocasi.weather_measurement.enums_exception.enums.TIME
import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.MethodRefPrint
import org.json.JSONObject

/**
 * Forecast for specific day and hour/time
 *
 * @author krystof-cejchan
 */
class ForecastAtHour(startOfJsonObject: JSONObject, val day: DAY, val time: TIME) {
    /**
     * @return The dew point is the temperature to which air must be cooled to become saturated with water vapor
     */
    val dewPointC: Int

    /**
     * @return The dew point is the temperature to which air must be cooled to become saturated with water vapor
     */
    val dewPointF: Int
    val feelsLikeC: Int
    val feelsLikeF: Int

    /**
     * @return The heat index, also known as the apparent temperature, is what the temperature feels like to the human body when relative humidity is combined with the air temperature
     */
    val heatIndexC: Int

    /**
     * @return The heat index, also known as the apparent temperature, is what the temperature feels like to the human body when relative humidity is combined with the air temperature
     */
    val heatIndexF: Int

    /**
     * @return Wind chill or windchill is the lowering of body temperature due to the passing-flow of lower-temperature air.
     */
    val windChillC: Int

    /**
     * @return Wind chill or windchill is the lowering of body temperature due to the passing-flow of lower-temperature air.
     */
    val windChillF: Int

    /**
     * @return A gust or wind gust is a brief increase in the speed of the wind, usually less than 20 seconds.
     */
    val windGustKmph: Int

    /**
     * @return A gust or wind gust is a brief increase in the speed of the wind, usually less than 20 seconds.
     */
    val windGustMiles: Int
    val chanceOfFog: Int
    val chanceOfFrost: Int
    val chanceOfHighTemperature: Int
    val chanceOfOvercast: Int
    val chanceOfRain: Int
    val chanceOfRemdry: Int
    val chanceOfSnow: Int
    val chanceOfSunshine: Int
    val chanceOfThunder: Int
    val chanceOfWindy: Int
    val cloudCover: Int
    val humidity: Int

    /**
     * @return Precipitation
     */
    val precipInches: Double

    /**
     * @return Precipitation
     */
    val precipMM: Double
    val pressure: Int
    val pressureInches: Int
    val temperatureC: Int
    val temperatureF: Int
    val timeInHundreds: Int
    val uvIndex: Int
    val visibility: Int
    val visibilityMiles: Int
    val weatherCode: Int
    val weatherDescription: String
    val windDir16Point: String
    val windDirDegree: Int
    val windSpeedKmph: Int
    val windSpeedMiles: Int

    init {
        val certHour = startOfJsonObject
            .getJSONArray("weather")
            .getJSONObject(DAY.Companion.getIndex(day))
            .getJSONArray("hourly").getJSONObject(TIME.Companion.getIndex(time))
        dewPointC = certHour.getInt("DewPointC")
        dewPointF = certHour.getInt("DewPointF")
        feelsLikeC = certHour.getInt("FeelsLikeC")
        feelsLikeF = certHour.getInt("FeelsLikeF")
        heatIndexC = certHour.getInt("HeatIndexC")
        heatIndexF = certHour.getInt("HeatIndexF")
        windChillC = certHour.getInt("WindChillC")
        windChillF = certHour.getInt("WindChillF")
        windGustKmph = certHour.getInt("WindGustKmph")
        windGustMiles = certHour.getInt("WindGustMiles")
        chanceOfFog = certHour.getInt("chanceoffog")
        chanceOfFrost = certHour.getInt("chanceoffrost")
        chanceOfHighTemperature = certHour.getInt("chanceofhightemp")
        chanceOfOvercast = certHour.getInt("chanceofovercast")
        chanceOfRain = certHour.getInt("chanceofrain")
        chanceOfRemdry = certHour.getInt("chanceofremdry")
        chanceOfSnow = certHour.getInt("chanceofsnow")
        chanceOfSunshine = certHour.getInt("chanceofsunshine")
        chanceOfThunder = certHour.getInt("chanceofthunder")
        chanceOfWindy = certHour.getInt("chanceofwindy")
        cloudCover = certHour.getInt("cloudcover")
        humidity = certHour.getInt("humidity")
        precipInches = certHour.getDouble("precipInches")
        precipMM = certHour.getDouble("precipMM")
        pressure = certHour.getInt("pressure")
        pressureInches = certHour.getInt("pressureInches")
        temperatureC = certHour.getInt("tempC")
        temperatureF = certHour.getInt("tempF")
        timeInHundreds = certHour.getInt("time")
        uvIndex = certHour.getInt("uvIndex")
        visibility = certHour.getInt("visibility")
        visibilityMiles = certHour.getInt("visibilityMiles")
        weatherCode = certHour.getInt("weatherCode")
        weatherDescription =
            certHour.getJSONArray("weatherDesc").getJSONObject(0).getString("value")
        windDir16Point = certHour.getString("winddir16Point")
        windDirDegree = certHour.getInt("winddirDegree")
        windSpeedKmph = certHour.getInt("windspeedKmph")
        windSpeedMiles = certHour.getInt("windspeedMiles")
    }

    /**
     * prints current object.toString to the console
     */
    fun print() {
        MethodRefPrint(this).print()
    }

    override fun toString(): String {
        return """
            ---ForecastAtHour---
            day=$day
            time=$time
            dewPointC=$dewPointC
            dewPointF=$dewPointF
            feelsLikeC=$feelsLikeC
            feelsLikeF=$feelsLikeF
            heatIndexC=$heatIndexC
            heatIndexF=$heatIndexF
            windChillC=$windChillC
            windChillF=$windChillF
            windGustKmph=$windGustKmph
            windGustMiles=$windGustMiles
            chanceOfFog=$chanceOfFog
            chanceOfFrost=$chanceOfFrost
            chanceOfHighTemperature=$chanceOfHighTemperature
            chanceOfOvercast=$chanceOfOvercast
            chanceOfRain=$chanceOfRain
            chanceOfRemdry=$chanceOfRemdry
            chanceOfSnow=$chanceOfSnow
            chanceOfSunshine=$chanceOfSunshine
            chanceOfThunder=$chanceOfThunder
            chanceOfWindy=$chanceOfWindy
            cloudCover=$cloudCover
            humidity=$humidity
            precipInches=$precipInches
            precipMM=$precipMM
            pressure=$pressure
            pressureInches=$pressureInches
            temperatureC=$temperatureC
            temperatureF=$temperatureF
            timeInHundreds=$timeInHundreds
            uvIndex=$uvIndex
            visibility=$visibility
            visibilityMiles=$visibilityMiles
            weatherCode=$weatherCode
            weatherDescription='$weatherDescription'
            windDir16Point='$windDir16Point'
            windDirDegree=$windDirDegree
            windSpeedKmph=$windSpeedKmph
            windSpeedMiles=$windSpeedMiles
            """.trimIndent()
    }
}