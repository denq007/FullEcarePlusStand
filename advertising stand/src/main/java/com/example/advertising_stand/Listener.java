package com.example.advertising_stand;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@MessageDriven(activationConfig = {@ActivationConfigProperty(
        propertyName = "destination",
        propertyValue = "java:/eCare/MyQueue"
)})

public class Listener implements MessageListener {
    @Inject
    WebSocket webSocket;
  /*  private List<Tariff> tariffs = new ArrayList<>();*/
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Listener.class);
    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            try {
                String messageText = textMessage.getText();
                Type listType = new TypeToken<ArrayList<Tariff>>(){}.getType();
                webSocket.setTariffs(new Gson().fromJson(messageText, listType));
                logger.info("MESSAGE!!!!!!!"+messageText);
                webSocket.sendMessage("update");
            } catch (JMSException e) {
                System.out.println(
                        "Error: " + e.getMessage());
            }
        }
    }

   /* public List<Tariff> getTariffs() {
        return tariffs;
    }

    public void setTariffs(List<Tariff> tariffs) {
        this.tariffs = tariffs;
    }*/
}
