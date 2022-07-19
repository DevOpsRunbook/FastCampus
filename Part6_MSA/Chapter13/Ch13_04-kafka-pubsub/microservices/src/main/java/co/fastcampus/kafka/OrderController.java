package co.fastcampus.kafka;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("order")
    public Order placeOrder(@RequestBody @NotNull(message = "Invalid Order") Order order) {
        return orderService.placeOrder(order);
    }

    @GetMapping("order/status/{orderUuid}")
    public OrderStatus statusCheck(@PathVariable("orderUuid") UUID orderUuid) {
        return orderService.statusCheck(orderUuid);
    }
}
