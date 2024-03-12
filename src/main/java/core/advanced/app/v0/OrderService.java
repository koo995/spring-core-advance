package core.advanced.app.v0;

import core.advanced.trace.TraceId;
import core.advanced.trace.TraceStatus;
import core.advanced.trace.hellotrace.HelloTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final HelloTrace trace;

    public void orderItem(TraceId traceId, String itemId) {

        TraceStatus status = null;
        try {
            status = trace.beginSync(traceId,"OrderService.orderItem()");
            orderRepository.save(status.getTraceId(), itemId);
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
