package cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.nearest_area.details

import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.MethodRefPrint
import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.nearest_area.NearestArea

/**
 * record <br></br>
 * more info for [NearestArea]
 *
 * @author krystof-cejchan
 */
class AreaInfo(
    val name: String,
    val latitude: String,
    val longitude: String,
    val population: String,
    val region: String
) {

    /**
     * prints current object.toString to the console
     */
    fun print() {
        MethodRefPrint(this).print()
    }

    override fun toString(): String {
        return """
            
            --AreaInfo--
            name=$name
            latitude=$latitude
            longitude=$longitude
            population=$population
            region=$region
            """.trimIndent()
    }
}