package org.hostile.fig.request;

import lombok.Getter;
import org.hostile.fig.FigWebClient;

import java.util.HashMap;
import java.util.Map;

@Getter
public class RequestBuilder {

    private final Map<String, String> requestHeaders = new HashMap<>();

    private String userAgent;

    private boolean customUserAgent;

    public RequestBuilder setHeader(String key, String value) {
        this.requestHeaders.put(key, value);
        return this;
    }

    public RequestBuilder setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        this.customUserAgent = true;

        return this;
    }

    public Map<String, String> getCustomRequestHeaders() {
        return this.requestHeaders;
    }

    public RequestBuilder() {
    }
}
