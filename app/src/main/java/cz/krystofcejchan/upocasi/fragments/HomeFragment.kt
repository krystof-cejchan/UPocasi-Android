package cz.krystofcejchan.upocasi.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import cz.krystofcejchan.upocasi.R
import cz.krystofcejchan.upocasi.objects.GreetingData
import cz.krystofcejchan.upocasi.objects.Preferences
import org.json.JSONException
import java.io.IOException
import java.text.DecimalFormat
import java.util.Locale

class HomeFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val greetings = view.findViewById<TextView>(R.id.greeting)
        val temp = view.findViewById<TextView>(R.id.textView)
        val imgView = view.findViewById<ImageView>(R.id.imageView)
        val greetingText = resources.getString(R.string.greeting)
        val preferences = Preferences(view.context)
        try {
            val greetingData =
                GreetingData(preferences.isCelsiusDefault, preferences.defaultLocation)
            val formattedTemp = DecimalFormat("##.0").format(greetingData.temperature)
            greetings.text = String.format(
                greetingText,
                greetingData.dayPhrase.toString().lowercase(Locale.getDefault()),
                greetingData.weatherState,
                formattedTemp,
                if (preferences.isCelsiusDefault) 'C' else 'F',
                greetingData.location
            )
            imgView.setImageResource(greetingData.image)
            temp.text = String.format("%s °%c", formattedTemp, 'C')
            val color =
                if (greetingData.temperature < 7) R.color.cold else if (greetingData.temperature < 20) R.color.warm else R.color.hot
            temp.setTextColor(resources.getColor(color))
        } catch (e: JSONException) {
            throw RuntimeException(e)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }
}