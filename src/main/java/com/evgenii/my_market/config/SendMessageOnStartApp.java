package com.evgenii.my_market.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * SendMessageOnStartApp class that send message to rabbitMq on start up and
 * when an ApplicationContext gets closed.
 *
 * @author Boznyakov Evgenii
 *
 */
@Component
@RequiredArgsConstructor
public class SendMessageOnStartApp {
    /**
     * Creates an instance of this class using constructor-based dependency injection.
     */
    private final MessageSender messageSender;

    /**
     * Send message to rabbit mq on start up
     */
    @EventListener(ContextRefreshedEvent.class)
    public void updateMessage() {
        messageSender.send("update");
    }

    /**
     * Send message to rabbit mq when an ApplicationContext gets closed
     */
    @EventListener(ContextClosedEvent.class)
    public void disconnectMessage() {
        messageSender.send("goodbye");
    }

}
