package org.hostile.fig.factory.impl;

import org.hostile.fig.factory.FigWebClientFactory;

import java.util.concurrent.Executors;

public class DefaultFigWebClientFactory extends FigWebClientFactory {

    public DefaultFigWebClientFactory() {
        setExecutor(Executors.newSingleThreadExecutor());
    }
}
