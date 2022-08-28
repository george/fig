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

    public FigWebClientFactory addDefaultRequestHeader(String key, String value) {
        defaultRequestHeaders.put(key, value);
        return this;
    }

    public FigWebClientFactory setDefaultUserAgent(String userAgent) {
        this.defaultUserAgent = userAgent;
        return this;
    }

    public FigWebClientFactory setExecutor(ExecutorService executor) {
        this.asyncExecutor = executor;
        return this;
    }

    public FigWebClient build() {
        return new FigWebClient(this);
    }
}
