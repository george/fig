package org.hostile.fig.request.method;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public enum RequestMethod {

    GET("GET"),
    POST("POST");

    private final String name;

}
