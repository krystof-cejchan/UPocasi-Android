package cz.krystofcejchan.upocasi.objects;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class Preferences {
    private final SharedPreferences sharedPreferences;

    public Preferences(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean isCelsiusDefault() {
        return sharedPreferences.getString("temp_unit", "celsius").equals("celsius");
    }

    public String getDefaultLocation() {
        return sharedPreferences.getString("location", "Olomouc");
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
