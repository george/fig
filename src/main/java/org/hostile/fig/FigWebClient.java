package org.hostile.fig;

import org.hostile.fig.factory.FigWebClientFactory;
import org.hostile.fig.factory.impl.DefaultFigWebClientFactory;
import org.hostile.fig.request.RequestBuilder;
import org.hostile.fig.response.WebResponse;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/**
 * @see FigWebClientFactory Constructs a Fig web client instance based off the
 *                          provided parameters. Can change default user agent,
 *                          request headers, etc.
 */
public class FigWebClient {

    private final Map<String, String> defaultRequestHeaders;
    private final ExecutorService asyncExecutor;

    /**
     * Constructs a FigWebClient instance based off of the provided factory
     * @param factory The template for the web client to use
     */
    public FigWebClient(FigWebClientFactory factory) {
        this.defaultRequestHeaders = factory.getDefaultRequestHeaders();
        this.asyncExecutor = factory.getAsyncExecutor();

        this.defaultRequestHeaders.put("user-agent", factory.getDefaultUserAgent());
    }

    public FigWebClient() {
        this(new DefaultFigWebClientFactory());
    }

    /**
     * Creates an HTTP GET request to the provided URL
     * @param url The URL to GET content from
     * @return A WebResponse instance containing the request response
     *         code and the site contents
     */
    public WebResponse get(String url) {
        return get(url, new RequestBuilder());
    }

    /**
     * Creates an HTTP POST request to the provided URL
     * @param url The URL to POST content to
     * @param data The request payload
     * @return A WebResponse instance containing the request response
     *         code and the site contents
     */
    public WebResponse post(String url, String data) {
        return post(url, data, new RequestBuilder());
    }

    /**
     * Asynchronously completes the provided request
     * @param url The request URL
     * @param callback A callback for the web response instance
     */
    public void get(String url, Consumer<WebResponse> callback) {
        get(url, new RequestBuilder(), callback);
    }

    /**
     * Asynchronously completes the provided request
     * @param url The request URL
     * @param callback A callback for the web response instance
     */
    public void post(String url, String data, Consumer<WebResponse> callback) {
        post(url, data, new RequestBuilder(), callback);
    }

    /**
     * Creates an HTTP GET request to the provided URL
     * @param requestUrl The URL to GET content from
     * @param requestBuilder A builder instance containing custom request headers
     * @return A WebResponse instance containing the request response
     *         code and the site contents
     */
    public WebResponse get(String requestUrl, RequestBuilder requestBuilder) {
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            defaultRequestHeaders.forEach(connection::addRequestProperty);
            requestBuilder.getRequestHeaders().forEach(connection::addRequestProperty);

            connection.connect();

            return new WebResponse(connection);
        } catch (Exception exc) {
            return new WebResponse(exc);
        }
    }

    /**
     * Creates an HTTP POST request to the provided URL
     * @param requestUrl The URL to POST content to
     * @param data The request payload
     * @param requestBuilder A builder instance containing custom request headers
     * @return A WebResponse instance containing the request response
     *         code and the site contents
     */
    public WebResponse post(String requestUrl, String data, RequestBuilder requestBuilder) {
        try {
            URL url = new URL(requestUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            defaultRequestHeaders.forEach(connection::addRequestProperty);
            requestBuilder.getRequestHeaders().forEach(connection::addRequestProperty);

            int payloadLength = data.getBytes().length;
            byte[] payloadArray = data.getBytes();

            connection.setFixedLengthStreamingMode(payloadLength);
            connection.setDoOutput(true);

            connection.connect();
            connection.getOutputStream().write(payloadArray);

            return new WebResponse(connection);
        } catch (Exception exc) {
            return new WebResponse(exc);
        }
    }

    /**
     * Sends the provided request to the async executor
     * @param requestUrl The request URL
     * @param requestBuilder The custom request parameters
     * @param callback A callback for the web response instance
     */
    public void get(String requestUrl, RequestBuilder requestBuilder, Consumer<WebResponse> callback) {
        asyncExecutor.execute(() -> {
            callback.accept(get(requestUrl, requestBuilder));
        });
    }

    /**
     * Sends the provided request to the async executor
     * @param requestUrl The request URL
     * @param data The request payload
     * @param requestBuilder The custom request parameters
     * @param callback A callback for the web response instance
     */
    public void post(String requestUrl, String data, RequestBuilder requestBuilder, Consumer<WebResponse> callback) {
        asyncExecutor.execute(() -> {
            callback.accept(post(requestUrl, data, requestBuilder));
        });
    }
}
