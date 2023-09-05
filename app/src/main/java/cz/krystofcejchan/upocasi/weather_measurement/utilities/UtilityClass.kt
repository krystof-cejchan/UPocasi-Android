package cz.krystofcejchan.upocasi.weather_measurement.utilities

import com.google.common.net.UrlEscapers
import cz.krystofcejchan.upocasi.weather_measurement.enums_exception.exceptions.CannotCreateInstance
import cz.krystofcejchan.upocasi.weather_measurement.enums_exception.exceptions.CouldNotFindLocation
import cz.krystofcejchan.upocasi.weather_measurement.enums_exception.exceptions.WeatherDataNotAccessible
import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.forecast.days.hour.ForecastAtHour
import org.apache.commons.io.IOUtils
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.URI
import java.net.URL
import java.nio.charset.StandardCharsets
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.regex.Pattern
import java.util.stream.Collectors

/**
 * Utility Class representing **design pattern** of the same name
 */
class UtilityClass private constructor() {
    init {
        throw CannotCreateInstance("This class serves as a utility class according to the design pattern of Utility Class")
    }

    /**
     * Storing saved data
     */
    object Storage {
        /**
         * storing all forecasts
         */
        private val listOfAllDaysAndItsTimes: MutableList<ForecastAtHour> = ArrayList()
        fun getListOfAllDaysAndItsTimes(): List<ForecastAtHour> {
            return listOfAllDaysAndItsTimes
        }

        fun addToListOfAllDaysAndItsTimes(forecast: ForecastAtHour) {
            if (listOfAllDaysAndItsTimes.stream().map { obj: ForecastAtHour -> obj.day }
                    .collect(Collectors.toList()).contains(forecast.day)
                && listOfAllDaysAndItsTimes.stream().map { obj: ForecastAtHour -> obj.time }
                    .collect(Collectors.toList()).contains(forecast.time)
            ) return
            listOfAllDaysAndItsTimes.add(forecast)
        }

        fun clearList() {
            listOfAllDaysAndItsTimes.clear()
        }

        fun removeElement(forecast: ForecastAtHour) {
            listOfAllDaysAndItsTimes.remove(forecast)
        }
    }

    /**
     * class for reading webpages
     */
    object WebPageReader {
        /**
         * checks whether param is link or not
         *
         * @param link web URL
         * @return true if link is truly link, else false
         */
        fun isLink(link: String): Boolean {
            val urlRegex =
                "((http://|https://)?(www.)?(([a-zA-Z0-9-]){2,}\\.){1,4}([a-zA-Z]){2,6}(/([a-zA-Z-_/.0-9#:?=&;,]*)?)?)"
            return Pattern.compile(urlRegex).matcher(link).find()
        }

        /**
         * gets and returns text from webpage
         *
         * @param webUrl webpage url
         * @return text from webpage
         */
        fun getTextFromWebpage(webUrl: String): String? {
            var webUrl = webUrl
            return try {
                if (!isLink(webUrl)) return null
                webUrl = webUrl.replace(" ", "%20")
                IOUtils.toString(URI.create(webUrl), StandardCharsets.UTF_8)
            } catch (e: IOException) {
                throw WeatherDataNotAccessible("Weather data could not be accessed; try again later")
            }
        }
    }

    companion object {
        /**
         * returns [LocalTime] from [StringBuilder].toString; can be null if [StringBuilder] does not contain any numbers or is null
         *
         * @param time [StringBuilder] as text
         * @return LocalTime if possible, else null
         */
        fun stringToLocalTime(time: StringBuilder): LocalTime? {
            if (!IsNumeric.containsNumbers(time.toString())) return null
            val hour_minutes = IntArray(2)
            val pm = time.toString().contains("PM")
            for (i in hour_minutes.indices) {
                hour_minutes[i] =
                    time.substring(0, time.indexOf(if (hour_minutes.size - 1 == i) " " else ":"))
                        .toInt()
                time.replace(0, time.indexOf(if (hour_minutes.size - 1 == i) " " else ":") + 1, "")
            }
            if (pm && hour_minutes[0] != 12) hour_minutes[0] += 12
            return LocalTime.of(hour_minutes[0], hour_minutes[1])
        }

        fun stringToDateTime(date: StringBuilder): LocalDateTime {
            val year_month_day = IntArray(3)
            val hour_minutes = IntArray(2)
            for (i in year_month_day.indices) {
                year_month_day[i] =
                    date.substring(0, date.indexOf(if (year_month_day.size - 1 == i) " " else "-"))
                        .toInt()
                date.replace(
                    0,
                    date.indexOf(if (year_month_day.size - 1 == i) " " else "-") + 1,
                    ""
                )
            }
            val pm = date.toString().contains("PM")
            for (i in hour_minutes.indices) {
                hour_minutes[i] =
                    date.substring(0, date.indexOf(if (hour_minutes.size - 1 == i) " " else ":"))
                        .toInt()
                date.replace(0, date.indexOf(if (hour_minutes.size - 1 == i) " " else ":") + 1, "")
            }
            if (pm && hour_minutes[0] != 12) hour_minutes[0] += 12
            return LocalDateTime.of(
                year_month_day[0],
                year_month_day[1],
                year_month_day[2],
                hour_minutes[0],
                hour_minutes[1]
            )
        }

        fun stringToDate(date: StringBuilder): LocalDate {
            val year_month_day = IntArray(3)
            for (i in year_month_day.indices) {
                year_month_day[i] = date.substring(
                    0,
                    if (year_month_day.size - 1 == i) date.length else date.indexOf("-")
                ).toInt()
                date.replace(
                    0,
                    if (year_month_day.size - 1 == i) date.length else date.indexOf("-") + 1,
                    ""
                )
            }
            return LocalDate.of(year_month_day[0], year_month_day[1], year_month_day[2])
        }

        @Throws(CouldNotFindLocation::class)
        fun getJson(location: String): JSONObject {
            return try {
                val jsonSource = IOUtils.toString(
                    URL(
                        UrlEscapers
                            .urlFragmentEscaper().escape("https://wttr.in/$location?format=j1")
                    ),
                    StandardCharsets.UTF_8
                )
                JSONObject(jsonSource)
            } catch (e: IOException) {
                throw CouldNotFindLocation("It seems the location you entered could not be found")
            } catch (e: JSONException) {
                throw CouldNotFindLocation("It seems the location you entered could not be found")
            }
        }
    }
}