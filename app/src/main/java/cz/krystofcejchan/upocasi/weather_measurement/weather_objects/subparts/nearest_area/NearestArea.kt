package cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.nearest_area

import cz.krystofcejchan.upocasi.weather_measurement.UtilityClass
import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.MethodRefPrint
import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.nearest_area.details.AreaInfo
import org.json.JSONObject

/**
 * Information about the area where the weather measurement was performed
 *
 * @author krystof-cejchan
 */
class NearestArea(location: String) {
    /**
     * @return returns country if possible
     */
    val country: String
    /**
     * @return AreaInfo class containing more data
     */
    /**
     * object containing more info for [NearestArea]
     */
    val areaInfo: AreaInfo

    init {
        val nearest_area: JSONObject =
            UtilityClass.Companion.getJson(location)!!.getJSONArray("nearest_area").getJSONObject(0)
        val v = "value"
        country = nearest_area.getJSONArray("country").getJSONObject(0).getString(v)
        areaInfo = AreaInfo(
            nearest_area.getJSONArray("areaName").getJSONObject(0).getString(v),
            nearest_area.getString("latitude"),
            nearest_area.getString("longitude"),
            nearest_area.getString("population"),
            nearest_area.getJSONArray("region").getJSONObject(0).getString(v)
        )
    }

    /**
     * prints current object.toString to the console
     */
    fun print() {
        MethodRefPrint(this).print()
    }

    override fun toString(): String {
        return """
            --NearestArea--
            country='$country
            areaInfo=$areaInfo
            """.trimIndent()
    }
}