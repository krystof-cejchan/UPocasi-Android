package cz.krystofcejchan.upocasi.objects

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class Preferences(context: Context?) {
    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context!!)
    }

    val isCelsiusDefault: Boolean
        get() = sharedPreferences.getString("temp_unit", "celsius") == "celsius"
    val defaultLocation: String?
        get() = sharedPreferences.getString("location", "Olomouc")
}