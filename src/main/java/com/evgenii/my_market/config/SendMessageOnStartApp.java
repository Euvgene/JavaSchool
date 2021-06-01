package com.evgenii.my_market.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendMessageOnStartApp {
    private final MessageSender messageSender;

    @EventListener(ContextRefreshedEvent.class)
    public void updateMessage() {
        messageSender.send("update");
    }

    @EventListener(ContextClosedEvent.class)
    public void disconnectMessage() {
        messageSender.send("goodbye");
    }

}
