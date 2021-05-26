package com.evgenii.my_market.config;

import com.evgenii.my_market.service.api.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendMessageOnStartApp {
    private final MessageService messageService;

    @EventListener(ContextRefreshedEvent.class)
    public void updateMessage() {
        messageService.send("update");
    }

    @EventListener(ContextClosedEvent.class)
    public void disconnectMessage() {
        messageService.send("goodbye");
    }

}
