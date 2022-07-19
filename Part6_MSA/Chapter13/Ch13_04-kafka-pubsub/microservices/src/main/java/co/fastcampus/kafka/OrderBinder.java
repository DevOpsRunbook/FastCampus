package co.fastcampus.kafka;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface OrderBinder {

    //spring.cloud.stream.bindings.inventoryChecking-in
    String INVENTORY_CHECKING_IN = "inventoryChecking-in";
    String INVENTORY_CHECKING_OUT = "inventoryChecking-out";

    @Input(INVENTORY_CHECKING_IN)
    SubscribableChannel inventoryCheckingIn();

    @Output(INVENTORY_CHECKING_OUT)
    MessageChannel inventoryCheckingOut();

    ////

    String ORDER_DLQ = "order-dlq";

    @Input(ORDER_DLQ)
    SubscribableChannel orderIn();

    ////

    String SHIPPING_IN = "shipping-in";
    String SHIPPING_OUT = "shipping-out";

    @Input(SHIPPING_IN)
    SubscribableChannel shippingIn();

    @Output(SHIPPING_OUT)
    MessageChannel shippingOut();
}
