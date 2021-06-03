package com.t_systems.ecare.eCare.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.ejb.DependsOn;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.jms.*;
//@Stateless
//@LocalBean
@Service
public class MessageSenderImpl implements MessageSender{

    private static final long TIME_TO_LIVE_MILLISEC = 30_000; //messages older than that value won't be delivered to destination

    @Resource(lookup = "java:/eCare/MyConnectionFactory")//inject with JNDI, resource
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "java:/eCare/MyQueue")//Queue
    private Destination destination;

    /**
     * Send message to JMS queue
     *
     * @param json message content in json formatted String
     */
 //   @Schedule(hour ="*", minute = "*", second = "*/5", persistent = false)   //called once per 5 second
    @Override
    public void sendMessage(String json) {
        try (
                QueueConnection connection = (QueueConnection) connectionFactory.createConnection();
                QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
                MessageProducer producer = session.createProducer(destination)//connect to Queue's name
        ) {
            TextMessage message = session.createTextMessage(json);
            producer.setTimeToLive(TIME_TO_LIVE_MILLISEC);
            producer.send(message);
            Logger.getLogger(MessageSenderImpl.class).info("Send jms:" + json);

        } catch (JMSException ex) {
            Logger.getLogger(MessageSenderImpl.class).error(ex.getMessage(), ex);
        }
    }
}
