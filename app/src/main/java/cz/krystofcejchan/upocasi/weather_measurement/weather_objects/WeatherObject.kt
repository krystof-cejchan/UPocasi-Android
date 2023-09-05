package cz.krystofcejchan.upocasi.weather_measurement.weather_objects

import cz.krystofcejchan.upocasi.weather_measurement.UtilityClass
import cz.krystofcejchan.upocasi.weather_measurement.enums_exception.enums.DAY
import cz.krystofcejchan.upocasi.weather_measurement.enums_exception.enums.TIME
import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.current_weather.CurrentCondition
import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.forecast.WeatherForecast
import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.nearest_area.NearestArea
import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.request.Request
import org.json.JSONObject
import java.util.Arrays

/**
 * Universal object from which you can get to more detailed objects [Request], [NearestArea], [WeatherForecast], [CurrentCondition], which lead
 * to another even more detailed objects or class fields
 *
 * @author krystof-cejchan
 */
class WeatherObject(location: String, times: Array<TIME>, vararg days: DAY) {
    /**
     * @return provided days in constructor
     */
    val days: Array<DAY>

    /**
     * @return provided TIMEs in constructor
     */
    val times: Array<TIME>

    /**
     *
     * @return location provided in the constructor of this class or its parent class <br></br>
     * if you need to get more detailed information, use [NearestArea]
     */
    val location: String

    /**
     * @return json as [JSONObject]
     */
    val json: JSONObject?

    /**
     * @return JSONObject as [String]
     */
    val jsonAsText: String?

    /**
     * @return CurrentCondition object
     */
    val currentCondition: CurrentCondition

    /**
     * @return Nearest Area object
     */
    val nearestArea: NearestArea

    /**
     * @return Request object
     */
    val request: Request

    /**
     * @return WeatherForecast object
     */
    val weatherForecast: WeatherForecast


    init {
        json = UtilityClass.getJson(location)
        jsonAsText =
            UtilityClass.WebPageReader.getTextFromWebpage("https://wttr.in/$location?format=j1")
        this.location = location
        this.days = arrayOf(*days)
        this.times = times
        currentCondition = CurrentCondition(location)
        nearestArea = NearestArea(location)
        request = Request(location)
        weatherForecast = WeatherForecast(location, times, *days)
    }

    /**
     * prints current object.toString to the console
     */
    fun print() {
        MethodRefPrint(this).print()
    }

    override fun toString(): String {
        return """
            ---WeatherObject---
            days=${Arrays.toString(days)}
            times=${Arrays.toString(times)}
            location='$location'
            json=$json
            jsonAsText='$jsonAsText'
            currentCondition=$currentCondition
            nearestArea=$nearestArea
            request=$request
            weatherForecast=$weatherForecast
            """.trimIndent()
    }
}