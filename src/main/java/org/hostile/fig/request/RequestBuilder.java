package org.hostile.fig.request;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class RequestBuilder {

    private final Map<String, String> requestHeaders = new HashMap<>();

    private String userAgent;

    private boolean customUserAgent;

    /**
     * Adds a request header with the provided key and value
     * @param key The header name
     * @param value The header value
     * @return The RequestBuilder instance
     */
    public RequestBuilder setHeader(String key, String value) {
        this.requestHeaders.put(key, value);
        return this;
    }

    /**
     * Sets the suer agent to a custom one
     * @param userAgent The user agent to be used in the request
     * @return The RequestBuilder instance
     */
    public RequestBuilder setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        this.customUserAgent = true;

        return this;
    }
}
