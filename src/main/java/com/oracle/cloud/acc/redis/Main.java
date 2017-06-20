package com.oracle.cloud.acc.redis;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class Main {

    public static void main(String[] args) throws IOException {
        String hostname = "0.0.0.0";
        String port = Optional.ofNullable(System.getenv("PORT")).orElse("8080");

        URI baseUri = UriBuilder.fromUri("http://" + hostname + "/").port(Integer.parseInt(port)).build();

        ResourceConfig config = new ResourceConfig(TodoResource.class)
                .register(MoxyJsonFeature.class);;

        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, config);
        System.out.println("Application accessible at " + baseUri.toString());

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Exiting");
                try {
                    server.shutdownNow();
                    System.out.println("Grizzly stopped");

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        }));
        server.start();
    }
}
