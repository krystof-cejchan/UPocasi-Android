package cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.request

import cz.krystofcejchan.upocasi.weather_measurement.UtilityClass
import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.MethodRefPrint
import org.json.JSONObject

/**
 * request data
 *
 * @author krystof-cejchan
 */
class Request(location: String) {
    val query: String
    val type: String

    init {
        val request: JSONObject =
            UtilityClass.Companion.getJson(location)!!.getJSONArray("request").getJSONObject(0)
        query = request.getString("query")
        type = request.getString("type")
    }

    /**
     * prints current object.toString to the console
     */
    fun print() {
        MethodRefPrint(this).print()
    }

    override fun toString(): String {
        return "Request{" +
                "query='" + query + '\'' +
                ", type='" + type + '\'' +
                '}'
    }
}