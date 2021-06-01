package com.evgenii.my_market.config;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class MessageSender {
    private static final String EXCHANGE_NAME = "infoTable";
    private Channel channel;

    @PostConstruct
    public void init() throws IOException, TimeoutException {
        // Establish connection with host localhost
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        Connection connection = connectionFactory.newConnection();
        channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
    }

    public void send(String message) {
        try {
            // Publish basic text message
            channel.basicPublish("", "infoTable", null, message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
