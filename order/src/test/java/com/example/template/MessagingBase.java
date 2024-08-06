package com.example.template;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.MessageVerifier;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MimeTypeUtils;

import labshopmessagingtest.OrderApplication;
import labshopmessagingtest.domain.Order;
import labshopmessagingtest.domain.OrderPlaced;
import labshopmessagingtest.infra.OrderController;

@RunWith(SpringRunner.class) 
@SpringBootTest(classes = OrderApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureMessageVerifier
public abstract class MessagingBase {

    private Logger logger = LoggerFactory.getLogger(MessagingBase.class);

    @Autowired
    OrderController orderController;

    @Autowired
    // Message interface to verify Contracts between services.
    MessageVerifier<Message<?>> messaging;

    

    @Before
    public void setup() {
        // any remaining messages on the "eventTopic" channel are cleared
        // makes that each test starts with a clean slate
        this.messaging.receive("eventTopic", 100, TimeUnit.MILLISECONDS);
    }

    public void orderPlaced() {

        String serializedJson = null;

        Order order = new Order();

        order.setId(1L);
        order.setCustomerId("1");
        order.setProductId("1");
        order.setProductName("TV");
        order.setQty(5);

        OrderPlaced orderPlaced = new OrderPlaced(order);
        // orderPlaced.setEventType("OrderPlaced");
        
        serializedJson = orderPlaced.toJson();

        logger.info("Sending message: {}", serializedJson);
        this.messaging.send(MessageBuilder
            .withPayload(serializedJson)
            .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
            .build(), "eventTopic");
    }
    
}