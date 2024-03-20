package core.advanced.app.v0;

import core.advanced.trace.callback.TraceCallback;
import core.advanced.trace.callback.TraceTemplate;
import core.advanced.trace.logtrace.LogTrace;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final TraceTemplate traceTemplate;

    public OrderService(OrderRepository orderRepository, LogTrace trace) {
        this.orderRepository = orderRepository;
        this.traceTemplate = new TraceTemplate(trace);
    }

    public void orderItem(String itemId) {
        traceTemplate.execute("OrderService.orderItem()", () -> {
            orderRepository.save(itemId);
            return null;
        });
    }


    /**
     * 위의 람다식은 아래의 식의 변형
     */
    public void orderItem2(String itemId) {
        traceTemplate.execute("OrderService.orderItem()", new TraceCallback<Object>() {
            @Override
            public Void call() {
                orderRepository.save(itemId);
                return null;
            }
        });
    }
}
