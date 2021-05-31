package com.evgenii.my_market.service;

import com.evgenii.my_market.service.api.MessageService;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class MessageServiceImpl implements MessageService {
    private static final String EXCHANGE_NAME = "infoTable";
    private Connection connection;
    private Channel channel;

    @PostConstruct
    public void init() throws IOException, TimeoutException {
        // Establish connection with host localhost
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        connection = connectionFactory.newConnection();
        channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
    }

    @Override
    public void send(String message) {
        try {
            // Publish basic text message
            channel.basicPublish("", "infoTable", null, message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
