package com.example.template;

import labshopmessagingtest.InventoryApplication;
import labshopmessagingtest.config.kafka.KafkaProcessor;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.StubFinder;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InventoryApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL, 
                        ids = "labshopmessagingtest:order:+:stubs")
public class OrderPlacedContactTest {

    @Autowired
    StubFinder stubFinder;

    @Autowired
    private KafkaProcessor processor;

    @Autowired
    // collect messages sent by a Spring Cloud Stream application
    private MessageCollector messageCollector;

    @Test
    public void testOnMessageReceived() {
        // Set Contract Label
        stubFinder.trigger("OrderPlaced");

        Message<String> received = (Message<String>) messageCollector.forChannel(processor.outboundTopic()).poll();
        System.out.println("<<<<<<< " + received.getPayload() + ">>>>>>");

        DocumentContext parsedJson = JsonPath.parse(received.getPayload());

        // Verify received message
        assertThat(parsedJson.read("$.eventType", String.class)).isEqualTo("OrderPlaced");
        assertThat(parsedJson.read("$.id", Long.class)).isGreaterThan(0L);
        assertThat(parsedJson.read("$.customerId", String.class)).matches("[\\S\\s]+");
        assertThat(parsedJson.read("$.productId", String.class)).matches("[\\S\\s]+");
        assertThat(parsedJson.read("$.productName", String.class)).matches("[\\S\\s]+");
        assertThat(parsedJson.read("$.qty", Integer.class)).isGreaterThan(0);

    }
}
