package cz.krystofcejchan.upocasi.weather_measurement.enums_exception.exceptions

import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.forecast.days.hour.IForecastDayTimesAndDays

/**
 * thrown if no data were found to the day and time <br></br>
 * thrown in [cz.krystofcejchan.lite_weather_lib.weather_objects.subparts.forecast.days.hour.IForecastDayTimesAndDays] if no weather data found
 */
class NoDataFoundForThisDayAndTime(errorMsg: String?) : RuntimeException(errorMsg)