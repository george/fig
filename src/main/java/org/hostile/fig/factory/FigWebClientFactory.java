package org.hostile.fig.factory;

import lombok.Getter;
import org.hostile.fig.FigWebClient;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

@Getter
public class FigWebClientFactory {

    private final Map<String, String> defaultRequestHeaders = new HashMap<>();

    private ExecutorService asyncExecutor;
    private String defaultUserAgent = "fig-requests";

    /**
     * Adds a default request header
     * @param key The request header name
     * @param value The request header value
     * @return A web client factory instance
     */
    public FigWebClientFactory addDefaultRequestHeader(String key, String value) {
        defaultRequestHeaders.put(key, value);
        return this;
    }

    /**
     * Sets the default user agent
     * @param userAgent The user agent
     * @return A web client factory instance
     */
    public FigWebClientFactory setDefaultUserAgent(String userAgent) {
        this.defaultUserAgent = userAgent;
        return this;
    }

    /**
     * Sets the executor for asynchronous requests
     * @param executor The executor service to use
     * @return A web client factory instance
     */
    public FigWebClientFactory setExecutor(ExecutorService executor) {
        this.asyncExecutor = executor;
        return this;
    }

    /**
     * @return A Fig web client instance based
     * off of the provided data
     */
    public FigWebClient build() {
        return new FigWebClient(this);
    }
}
