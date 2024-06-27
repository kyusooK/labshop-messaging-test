package labshopmessagingtest.domain;

import java.util.*;
import labshopmessagingtest.domain.*;
import labshopmessagingtest.infra.AbstractEvent;
import lombok.*;

@Data
@ToString
public class OrderPlaced extends AbstractEvent {

    private Long id;
    private String customerId;
    private String productId;
    private String productName;
    private Integer qty;
}
