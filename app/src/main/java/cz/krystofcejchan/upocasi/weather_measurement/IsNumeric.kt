package cz.krystofcejchan.upocasi.weather_measurement

object IsNumeric {
    fun containsNumbers(text: String): Boolean {
        return text.isEmpty() || text.matches(Regex.fromLiteral("\\d+"))
    }
}