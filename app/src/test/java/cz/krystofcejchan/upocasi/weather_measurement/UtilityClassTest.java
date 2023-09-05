package cz.krystofcejchan.upocasi.weather_measurement;

import static java.nio.charset.StandardCharsets.UTF_8;

import org.json.JSONException;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class UtilityClassTest {
    @Test
    public void getJsonFromWeb() throws IOException, JSONException {
        StringBuilder builder = new StringBuilder();
        String s = "https://wttr.in/" + "Olomouc" + "?format=j1";
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(s).openStream(), UTF_8))) {
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                builder.append(str);
            }
        }
        String jsonStr = builder.toString();
        System.out.println(jsonStr);
    }

}