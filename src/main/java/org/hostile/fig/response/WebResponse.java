package org.hostile.fig.response;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class WebResponse {

    private final int responseCode;
    private final String data;

    @SneakyThrows
    public WebResponse(HttpURLConnection connection) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder builder = new StringBuilder();

        String line;

        while((line = reader.readLine()) != null) {
            builder.append(line);
        }

        this.data = builder.toString();
        this.responseCode = connection.getResponseCode();
    }

    public <T extends JsonElement> T asJson(Gson gson, Class<T> clazz) {
        return gson.fromJson(data, clazz);
    }

    public String getData() {
        return data;
    }

    public int getResponseCode() {
        return responseCode;
    }
}
