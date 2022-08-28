package org.hostile.fig;

import org.hostile.fig.factory.FigWebClientFactory;
import org.hostile.fig.factory.impl.DefaultFigWebClientFactory;
import org.hostile.fig.request.RequestBuilder;
import org.hostile.fig.response.WebResponse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

public class FigWebClient {

    private final Map<String, String> defaultRequestHeaders;
    private final ExecutorService asyncExecutor;

    public FigWebClient(FigWebClientFactory factory) {
        this.defaultRequestHeaders = factory.getDefaultRequestHeaders();
        this.asyncExecutor = factory.getAsyncExecutor();

        this.defaultRequestHeaders.put("user-agent", factory.getDefaultUserAgent());
    }

    public FigWebClient() {
        this(new DefaultFigWebClientFactory());
    }

    public WebResponse get(String url) throws IOException {
        return get(url, new RequestBuilder());
    }

    public WebResponse post(String url, String data) throws IOException {
        return post(url, data, new RequestBuilder());
    }

    public WebResponse get(String requestUrl, RequestBuilder requestBuilder) throws IOException {
        URL url = new URL(requestUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        defaultRequestHeaders.forEach(connection::addRequestProperty);
        requestBuilder.getCustomRequestHeaders().forEach(connection::addRequestProperty);

        connection.connect();

        return new WebResponse(connection);
    }

    public WebResponse post(String requestUrl, String data, RequestBuilder requestBuilder) throws IOException {
        URL url = new URL(requestUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        defaultRequestHeaders.forEach(connection::addRequestProperty);
        requestBuilder.getCustomRequestHeaders().forEach(connection::addRequestProperty);

        int payloadLength = data.getBytes().length;
        byte[] payloadArray = data.getBytes();

        connection.setFixedLengthStreamingMode(payloadLength);
        connection.setDoOutput(true);

        connection.connect();
        connection.getOutputStream().write(payloadArray);

        return new WebResponse(connection);
    }

    public void getAsync(String url, Consumer<WebResponse> callback) {
        getAsync(url, new RequestBuilder(), callback);
    }

    public void postAsync(String url, String data, Consumer<WebResponse> callback) {
        postAsync(url, data, new RequestBuilder(), callback);
    }

    public void getAsync(String requestUrl, RequestBuilder requestBuilder, Consumer<WebResponse> callback) {
        asyncExecutor.execute(() -> {
            WebResponse response;

            try {
                response = get(requestUrl, requestBuilder);
                callback.accept(response);
            } catch (IOException ignored) {}
        });
    }

    public void postAsync(String requestUrl, String data, RequestBuilder requestBuilder, Consumer<WebResponse> callback) {
        asyncExecutor.execute(() -> {
            WebResponse response;

            try {
                response = post(requestUrl, data, requestBuilder);
                callback.accept(response);
            } catch (IOException ignored) {}
        });
    }
}
