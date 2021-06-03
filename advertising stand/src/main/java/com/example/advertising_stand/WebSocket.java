package com.example.advertising_stand;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Singleton
@ApplicationScoped
@Named
public class WebSocket{
    private static final String URI = "http://localhost:8080/hotTariffs";
    private static final Logger logger = org.apache.log4j.Logger.getLogger(WebSocket.class);
    @Inject
    @Push(channel = "websocket")
    private PushContext pushContext;
    private List<Tariff> tariffs = new CopyOnWriteArrayList<>();//All threads will work with their own collection

    public List<Tariff> getTariffs() {
        return tariffs;
    }

    public void setTariffs(List<Tariff> tariffs) {
        this.tariffs.clear();
        this.tariffs = tariffs;
    }

    public void sendMessage(String message) {
        logger.info("ubdate data");
        pushContext.send(message);
    }

    @PostConstruct
    public void askData() {
        try {
            URL url = new URL(URI);
            HttpURLConnection connection = (HttpURLConnection) (url.openConnection());
            int status = connection.getResponseCode();
            logger.info("askData() response status: " + status);
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
        }
    }
}
