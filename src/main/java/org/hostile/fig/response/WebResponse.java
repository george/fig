package org.hostile.fig.response;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

@Getter
public class WebResponse {

    private final int responseCode;
    private final String data;

    private Exception exception;

    /**
     * Constructs a WebResponse instance using an
     * exception, allowing for simple error handling
     * @param exception The exception caused by the connection
     */
    public WebResponse(Exception exception) {
        this.exception = exception;

        this.data = null;
        this.responseCode = -1;
    }

    /**
     * Constructs a WebResponse instance from a connection
     * @param connection The connection containing the response
     *                   code and response data
     */
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

    /**
     * Returns a JSON element instance of the content data
     * @param gson The GSON instance to parse the JSON data with
     * @param clazz The JSON element type
     * @param <T> The JSON element type
     * @return The parsed response data
     */
    public <T extends JsonElement> T asJson(Gson gson, Class<T> clazz) {
        return gson.fromJson(data, clazz);
    }

    /**
     * @return If an error occurred while completing the request
     */
    public boolean hasException() {
        return exception != null;
    }
}
