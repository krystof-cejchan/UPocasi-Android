package cz.krystofcejchan.upocasi.fragments.recycler_view_layout_data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cz.krystofcejchan.upocasi.R;

public final class WeatherRecyclerViewAdapter extends RecyclerView.Adapter<WeatherRecyclerViewAdapter.ViewHolder> {
    private final Context context;
    private final List<RowWeatherModule> data;

    public WeatherRecyclerViewAdapter(Context context, List<RowWeatherModule> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public WeatherRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_layout_in_recyclerview, parent, false);
        return new WeatherRecyclerViewAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull WeatherRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.key.setText(data.get(position).getTitle());
        holder.value.setText(data.get(position).getValue());
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return this.data.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        TextView key, value;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            key = itemView.findViewById(R.id.key);
            value = itemView.findViewById(R.id.value);
        }
    }
}
