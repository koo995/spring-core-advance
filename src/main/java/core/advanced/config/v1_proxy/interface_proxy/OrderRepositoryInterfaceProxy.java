package core.advanced.config.v1_proxy.interface_proxy;

import core.advanced.app.v1.OrderRepositoryV1;
import core.advanced.trace.TraceStatus;
import core.advanced.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderRepositoryInterfaceProxy implements OrderRepositoryV1 {

    /**
     * 당연히 실제 객체를 참조할 수 있어야 한다.
     */
    private final OrderRepositoryV1 target;
    private final LogTrace logTrace;

    @Override
    public void save(String itemId) {

        TraceStatus status = null;
        try {
            status = logTrace.begin("OrderRepository.request()");
            //target 호출
            target.save(itemId);
            logTrace.end(status);
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
