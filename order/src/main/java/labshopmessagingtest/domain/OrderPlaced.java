package labshopmessagingtest.domain;

import java.time.LocalDate;
import java.util.*;
import labshopmessagingtest.domain.*;
import labshopmessagingtest.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class OrderPlaced extends AbstractEvent {

    private Long id;
    private String customerId;
    private String productId;
    private String productName;
    private Integer qty;

    public OrderPlaced(Order aggregate) {
        super(aggregate);
    }

    public OrderPlaced() {
        super();
    }
}
//>>> DDD / Domain Event
