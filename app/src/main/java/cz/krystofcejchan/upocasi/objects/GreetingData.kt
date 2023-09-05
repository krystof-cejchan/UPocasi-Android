package cz.krystofcejchan.upocasi.objects

import cz.krystofcejchan.upocasi.R.drawable
import cz.krystofcejchan.upocasi.utilities.enums_exceptions.DayPhrases
import cz.krystofcejchan.upocasi.weather_measurement.enums_exception.enums.DAY
import cz.krystofcejchan.upocasi.weather_measurement.enums_exception.enums.TIME
import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.WeatherObject
import java.time.LocalTime
import java.util.Arrays

class GreetingData(celsius: Boolean, location: String?) {
    val dayPhrase: DayPhrases
    val weatherState: String?
    val temperature: Double
    val location: String?
    val image: Int

    init {
        val currentTime = LocalTime.now()
        dayPhrase = Arrays.stream(DayPhrases.values())
            .sorted()
            .filter { phrase: DayPhrases -> currentTime.isBefore(phrase.time) || currentTime == phrase.time }
            .findFirst()
            .orElse(DayPhrases.DAY)
        this.location = location
        val weatherObject = WeatherObject(this.location!!, arrayOf(TIME.ALL), DAY.ALL)
        val currentCondition = weatherObject.currentCondition
        weatherState = currentCondition.weatherDescription
        temperature = (if (celsius) currentCondition.temp_C else currentCondition.temp_F).toDouble()
        image = generateImage(currentCondition.weatherCode)
    }

    private fun generateImage(weatherCode: Int): Int {
        return when (weatherCode) {
            113 -> drawable.sunny
            116 -> drawable.partly_cloudy
            119, 122 -> drawable.cloudy
            143, 248, 260 -> drawable.fog
            176, 305, 308 -> drawable.heavy_rain
            179, 182 -> drawable.snowy
            185, 263, 266, 293, 296, 299, 302, 353, 356, 359, 362, 365 -> drawable.rainy
            200, 389, 392, 395 -> drawable.thunderstorm
            227 -> drawable.windy
            230, 329, 332 -> drawable.snow
            281, 368, 371 -> drawable.snowy
            284, 335, 338 -> drawable.snow
            320, 323 -> drawable.rainy
            350, 374, 377, 386 -> drawable.ice
            else -> drawable.unknown
        }
    }
}