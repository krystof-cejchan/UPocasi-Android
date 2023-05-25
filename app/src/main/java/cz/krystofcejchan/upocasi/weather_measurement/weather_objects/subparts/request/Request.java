package cz.krystofcejchan.upocasi.weather_measurement.weather_objects.subparts.request;

import org.json.JSONException;
import org.json.JSONObject;

import cz.krystofcejchan.upocasi.weather_measurement.UtilityClass;
import cz.krystofcejchan.upocasi.weather_measurement.enums_exception.exceptions.CouldNotFindLocation;
import cz.krystofcejchan.upocasi.weather_measurement.weather_objects.MethodRefPrint;

/**
 * request data
 *
 * @author krystof-cejchan
 */
public final class Request {
    private final String query;
    private final String type;

    public Request(String location) throws CouldNotFindLocation, JSONException {
        JSONObject request = UtilityClass.getJson(location).getJSONArray("request").getJSONObject(0);
        query = request.getString("query");
        type = request.getString("type");
    }

    public String getQuery() {
        return query;
    }

    public String getType() {
        return type;
    }

    /**
     * prints current object.toString to the console
     */
    public void print() {
        new MethodRefPrint<>(this).print();
    }

    @Override
    public String toString() {
        return "Request{" +
                "query='" + query + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
