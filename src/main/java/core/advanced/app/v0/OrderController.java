package core.advanced.app.v0;

import core.advanced.trace.callback.TraceTemplate;
import core.advanced.trace.logtrace.LogTrace;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * traceTemplate를 처음부터 스프링 빈으로 등록하고 주입받아도 된다. 이 부분은 선택이다.
 */
@RestController
public class OrderController {

    private final OrderService orderService;
    private final TraceTemplate traceTemplate;

    public OrderController(OrderService orderService, LogTrace logTrace) {
        this.orderService = orderService;
        this.traceTemplate = new TraceTemplate(logTrace);
    }

    @GetMapping("/v0/request")
    public String request(String itemId) {
        return traceTemplate.execute("OrderController.request()", () -> {
            orderService.orderItem(itemId);
            return "ok";
        });
    }
}
