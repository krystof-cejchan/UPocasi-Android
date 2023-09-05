package cz.krystofcejchan.upocasi.weather_measurement.weather_objects

import java.util.function.Consumer

/**
 * prints object to the console, if possible object.toString() will be printed
 *
 * @param <T> Generics
</T> */
class MethodRefPrint<T>(var t: T) {
    /**
     * prints object to the console using Functional Interface
     */
    fun print() {
        val pRef = Consumer { x: T -> println(x) }
        pRef.accept(t)
    }
}