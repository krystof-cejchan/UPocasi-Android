package cz.krystofcejchan.upocasi.fragments.recycler_view_layout_data;

public class RowWeatherModule {
    final private String title;
    final private String value;

    public RowWeatherModule(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public String getValue() {
        return value;
    }
}
