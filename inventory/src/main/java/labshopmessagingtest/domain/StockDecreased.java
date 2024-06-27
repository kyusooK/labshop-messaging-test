package labshopmessagingtest.domain;

import java.time.LocalDate;
import java.util.*;
import labshopmessagingtest.domain.*;
import labshopmessagingtest.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class StockDecreased extends AbstractEvent {

    private Long id;
    private Integer stock;

    public StockDecreased(Inventory aggregate) {
        super(aggregate);
    }

    public StockDecreased() {
        super();
    }
}
//>>> DDD / Domain Event
