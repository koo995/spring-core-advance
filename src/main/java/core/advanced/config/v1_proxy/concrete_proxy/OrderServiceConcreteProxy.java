package core.advanced.config.v1_proxy.concrete_proxy;


import core.advanced.app.v2.OrderServiceV2;
import core.advanced.trace.TraceStatus;
import core.advanced.trace.logtrace.LogTrace;

public class OrderServiceConcreteProxy extends OrderServiceV2 {

    private final OrderServiceV2 target;
    private final LogTrace logTrace;

    /**
     * OrderServiceV2을 상속받을려니까 생성자가 존재한다.(기본 생성자만 있으면 상관없는데 그게 아닌 생성자니까)
     * 자바언어의 기본으로 부모의 생성자가 호출됨(super관련)
     * 자식타입에서 생성자를 호출해야함. 문법상 어쩔수없이 부모의 생성자를 호출해야 하고, 전혀 안쓸거니까 null을 어쩔수없이 넣는다.
     */
    public OrderServiceConcreteProxy(OrderServiceV2 target, LogTrace logTrace) {
        super(null);
        this.target = target;
        this.logTrace = logTrace;
    }

    @Override
    public void orderItem(String itemId) {

        TraceStatus status = null;
        try {
            status = logTrace.begin("OrderService.orderItem()");
            //target 호출
            target.orderItem(itemId);
            logTrace.end(status);
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
