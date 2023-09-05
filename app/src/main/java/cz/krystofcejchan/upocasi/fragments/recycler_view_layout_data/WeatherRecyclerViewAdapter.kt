package cz.krystofcejchan.upocasi.fragments.recycler_view_layout_data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cz.krystofcejchan.upocasi.R

class WeatherRecyclerViewAdapter(
    private val context: Context?,
    private val data: List<RowWeatherModule>
) : RecyclerView.Adapter<WeatherRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.item_layout_in_recyclerview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.key.text = data[position].title
        holder.value.text = data[position].value
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var key: TextView
        var value: TextView

        init {
            key = itemView.findViewById(R.id.key)
            value = itemView.findViewById(R.id.value)
        }
    }
}