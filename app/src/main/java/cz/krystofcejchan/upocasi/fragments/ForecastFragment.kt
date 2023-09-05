package cz.krystofcejchan.upocasi.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import cz.krystofcejchan.upocasi.R
import cz.krystofcejchan.upocasi.fragments.recycler_view_layout_data.RowWeatherModule
import cz.krystofcejchan.upocasi.fragments.recycler_view_layout_data.WeatherRecyclerViewAdapter
import cz.krystofcejchan.upocasi.objects.Preferences
import cz.krystofcejchan.upocasi.weather_measurement.enums_exception.enums.DAY
import cz.krystofcejchan.upocasi.weather_measurement.enums_exception.enums.TIME
import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.forecast.WeatherForecast
import org.json.JSONException
import java.io.IOException
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ForecastFragment : Fragment(), OnSeekBarChangeListener,
    ChipGroup.OnCheckedStateChangeListener {
    var timeShow: TextView? = null
    var dayShow: TextView? = null
    var weatherForecast: WeatherForecast? = null
    var todayChip: Chip? = null
    var tomorrowChip: Chip? = null
    var afterTomorrowChip: Chip? = null
    var recyclerView: RecyclerView? = null
    private val rowWeatherModules: MutableList<RowWeatherModule> = ArrayList()
    var pickedTime: TIME? = null
    var pickedDay: DAY? = null
    private var preferences: Preferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forecast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = Preferences(view.context)
        recyclerView = view.findViewById(R.id.forecastRecyclerView)
        val timeSeekBar = view.findViewById<SeekBar>(R.id.seekBar)
        val daysChipGroup = view.findViewById<ChipGroup>(R.id.chipGroup)
        timeSeekBar.setOnSeekBarChangeListener(this)
        daysChipGroup.setOnCheckedStateChangeListener(this)
        timeShow = view.findViewById(R.id.seekBarTime)
        dayShow = view.findViewById(R.id.seekBarDate)
        todayChip = view.findViewById(R.id.chip)
        tomorrowChip = view.findViewById(R.id.chip2)
        afterTomorrowChip = view.findViewById(R.id.chip3)
        setTime(0)
        setDate(todayChip!!.id)
        try {
            weatherForecast =
                preferences!!.defaultLocation?.let { WeatherForecast(it, TIME.ALL, DAY.ALL) }
        } catch (e: IOException) {
            Toast.makeText(this.context, "Can't get forecast", Toast.LENGTH_SHORT).show()
        } catch (e: JSONException) {
            Toast.makeText(this.context, "Can't get forecast", Toast.LENGTH_SHORT).show()
        }
        setRecyclerViewData()
    }

    override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
        setTime(i)
        setRecyclerViewData()
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {}
    override fun onStopTrackingTouch(seekBar: SeekBar) {}
    override fun onCheckedChanged(group: ChipGroup, checkedIds: List<Int>) {
        setDate(checkedIds[0])
        setRecyclerViewData()
    }

    private fun setTime(i: Int) {
        pickedTime = TIME.Companion.getAllEnumsExcept(TIME.ALL)[i]
        val pickedLocalTime = pickedTime!!.time;
        val date = pickedLocalTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        timeShow!!.text = date
    }

    private fun setDate(integer: Int) {
        val date = LocalDate.now()
        val plusDays =
            if (todayChip!!.id == integer) 0 else if (tomorrowChip!!.id == integer) 1 else 2
        pickedDay =
            if (plusDays == 0) DAY.TODAY else if (plusDays == 1) DAY.TOMORROW else DAY.AFTER_TOMORROW
        dayShow!!.text =
            date.plusDays(plusDays.toLong()).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    }

    private fun setRowWeatherModules(day: DAY?, time: TIME?) {
        rowWeatherModules.clear()
        val celsius = preferences!!.isCelsiusDefault
        val temp = if (celsius) " °C" else " °F"
        val currentCondition = weatherForecast!!.getForecastFor(day!!, time!!)
        val tempFormat = DecimalFormat("##.0")
        val humFormat = DecimalFormat("###")
        rowWeatherModules.add(RowWeatherModule("Location", weatherForecast!!.location))
        rowWeatherModules.add(
            RowWeatherModule(
                "Weather condition",
                currentCondition.weatherDescription
            )
        )
        rowWeatherModules.add(
            RowWeatherModule(
                "Temperature",
                tempFormat.format((if (celsius) currentCondition.temperatureC else currentCondition.temperatureF).toLong()) + temp
            )
        )
        rowWeatherModules.add(
            RowWeatherModule(
                "Feels-like temperature",
                tempFormat.format((if (celsius) currentCondition.feelsLikeC else currentCondition.feelsLikeF).toLong()) + temp
            )
        )
        rowWeatherModules.add(
            RowWeatherModule(
                "Humidity",
                humFormat.format(currentCondition.humidity.toLong()) + " %"
            )
        )
        rowWeatherModules.add(
            RowWeatherModule(
                "Precipitation",
                tempFormat.format(currentCondition.precipMM) + " mm"
            )
        )
        rowWeatherModules.add(
            RowWeatherModule(
                "Cloud cover",
                humFormat.format(currentCondition.cloudCover.toLong()) + " %"
            )
        )
        rowWeatherModules.add(
            RowWeatherModule(
                "UV-index",
                humFormat.format(currentCondition.uvIndex.toLong())
            )
        )
        rowWeatherModules.add(
            RowWeatherModule(
                "Wind speed",
                tempFormat.format(currentCondition.windSpeedKmph.toLong()) + " km/h"
            )
        )
    }

    private fun setRecyclerViewData() {
        setRowWeatherModules(pickedDay, pickedTime)
        val adapter = WeatherRecyclerViewAdapter(context, rowWeatherModules)
        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = LinearLayoutManager(this.context)
    }
}