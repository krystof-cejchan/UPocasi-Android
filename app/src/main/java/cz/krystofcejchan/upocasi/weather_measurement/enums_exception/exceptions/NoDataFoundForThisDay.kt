package cz.krystofcejchan.upocasi.weather_measurement.enums_exception.exceptions

import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.forecast.WeatherForecast

/**
 * thrown if no data were found to the day <br></br>
 * thrown in [cz.krystofcejchan.lite_weather_lib.weather_objects.subparts.forecast.WeatherForecast] if user calls
 * getToday(), getTomorrow ... methods while not including these days in constructor
 */
class NoDataFoundForThisDay : RuntimeException {
    constructor(errorMsg: String?) : super(errorMsg) {}
    constructor(errorMsg: String?, throwable: Throwable?) : super(errorMsg, throwable) {}
    constructor() : super() {}

    /**
     * method throwing [NoDataFoundForThisDayAndTime]
     *
     * @param msg message to be displayed
     * @return NoDataFoundForThisDay with time
     */
    fun addTime(msg: String?): NoDataFoundForThisDayAndTime {
        return NoDataFoundForThisDayAndTime(msg)
    }
}