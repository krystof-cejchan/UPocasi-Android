package cz.krystofcejchan.upocasi.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import cz.krystofcejchan.upocasi.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}