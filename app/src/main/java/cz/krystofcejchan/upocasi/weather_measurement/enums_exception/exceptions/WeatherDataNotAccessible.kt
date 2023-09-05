package cz.krystofcejchan.upocasi.weather_measurement.enums_exception.exceptions

/**
 * weather data could not be accessed, check internet connection or make sure that location is correct [CouldNotFindLocation]
 */
class WeatherDataNotAccessible(msg: String?) : RuntimeException(msg)