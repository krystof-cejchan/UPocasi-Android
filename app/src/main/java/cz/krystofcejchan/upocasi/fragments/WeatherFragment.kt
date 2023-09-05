package cz.krystofcejchan.upocasi.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cz.krystofcejchan.upocasi.R
import cz.krystofcejchan.upocasi.fragments.recycler_view_layout_data.RowWeatherModule
import cz.krystofcejchan.upocasi.fragments.recycler_view_layout_data.WeatherRecyclerViewAdapter
import cz.krystofcejchan.upocasi.objects.Preferences
import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.current_weather.CurrentCondition
import org.json.JSONException
import java.text.DecimalFormat

class WeatherFragment : Fragment() {
    private var currentCondition: CurrentCondition? = null
    private val rowWeatherModules: MutableList<RowWeatherModule> = ArrayList()
    private var pf: Preferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pf = Preferences(view.context)
        val recyclerView = view.findViewById<RecyclerView>(R.id.RV_weather_data_items)
        try {
            currentCondition = pf!!.defaultLocation?.let { CurrentCondition(it) }
        } catch (e: JSONException) {
            Toast.makeText(this.context, "Error getting the weather data", Toast.LENGTH_LONG).show()
        }
        setRowWeatherModules()
        val adapter = WeatherRecyclerViewAdapter(context, rowWeatherModules)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)
    }

    private fun setRowWeatherModules() {
        val celsius = pf!!.isCelsiusDefault
        val temp = if (celsius) " °C" else " °F"
        val tempFormat = DecimalFormat("##.0")
        val humFormat = DecimalFormat("###")
        rowWeatherModules.add(RowWeatherModule("Location", currentCondition!!.location))
        rowWeatherModules.add(
            RowWeatherModule(
                "Weather condition",
                currentCondition!!.weatherDescription
            )
        )
        rowWeatherModules.add(
            RowWeatherModule(
                "Temperature",
                tempFormat.format((if (celsius) currentCondition!!.temp_C else currentCondition!!.temp_F).toLong()) + temp
            )
        )
        rowWeatherModules.add(
            RowWeatherModule(
                "Feels-like temperature",
                tempFormat.format((if (celsius) currentCondition!!.feelsLikeC else currentCondition!!.feelsLikeF).toLong()) + temp
            )
        )
        rowWeatherModules.add(
            RowWeatherModule(
                "Humidity",
                humFormat.format(currentCondition!!.humidity.toLong()) + " %"
            )
        )
        rowWeatherModules.add(
            RowWeatherModule(
                "Precipitation",
                tempFormat.format(currentCondition!!.precipMM) + " mm"
            )
        )
        rowWeatherModules.add(
            RowWeatherModule(
                "Cloud cover",
                humFormat.format(currentCondition!!.cloudCover.toLong()) + " %"
            )
        )
        rowWeatherModules.add(
            RowWeatherModule(
                "UV-index",
                humFormat.format(currentCondition!!.uvIndex.toLong())
            )
        )
        rowWeatherModules.add(
            RowWeatherModule(
                "Wind speed",
                tempFormat.format(currentCondition!!.windSpeedKmph.toLong()) + " km/h"
            )
        )
    }
}