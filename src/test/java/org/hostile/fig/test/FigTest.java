package org.hostile.fig.test;

import com.sun.net.httpserver.HttpServer;
import lombok.SneakyThrows;
import org.hostile.fig.FigWebClient;
import org.hostile.fig.response.WebResponse;
import org.junit.Test;

import java.io.InputStream;
import java.net.InetSocketAddress;

import static org.junit.Assert.assertEquals;

public class FigTest {

    private final FigWebClient webClient = new FigWebClient();

    @SneakyThrows
    @Test
    public void testGet() {
        HttpServer server = HttpServer.create(new InetSocketAddress(3000), 0);

        server.createContext("/get", (exchange) -> {
            String response = "Hello, world!";

            byte[] data = response.getBytes();

            exchange.sendResponseHeaders(200, data.length);
            exchange.getResponseBody().write(data);
        });

        server.start();

        WebResponse response = webClient.get("http://127.0.0.1:3000/get");

        assertEquals(response.getResponseCode(), 200);
        assertEquals(response.getData(), "Hello, world!");
    }

    @SneakyThrows
    @Test
    public void testPost() {
        HttpServer server = HttpServer.create(new InetSocketAddress(3001), 0);

        server.createContext("/post", (exchange) -> {
            int length = Integer.parseInt(exchange.getRequestHeaders().getFirst("Content-Length"));
            InputStream inputStream = exchange.getRequestBody();

            byte[] data = new byte[length];
            inputStream.read(data);

            exchange.sendResponseHeaders(200, data.length);
            exchange.getResponseBody().write(data);
        });

        server.start();

        WebResponse response = webClient.post("http://127.0.0.1:3001/post", "Hello, world!");

        assertEquals(response.getResponseCode(), 200);
        assertEquals(response.getData(), "Hello, world!");
    }
}
