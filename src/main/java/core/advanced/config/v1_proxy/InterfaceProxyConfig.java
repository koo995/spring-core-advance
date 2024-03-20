package core.advanced.config.v1_proxy;


import core.advanced.app.v1.*;
import core.advanced.config.v1_proxy.interface_proxy.OrderControllerInterfaceProxy;
import core.advanced.config.v1_proxy.interface_proxy.OrderRepositoryInterfaceProxy;
import core.advanced.config.v1_proxy.interface_proxy.OrderServiceInterfaceProxy;
import core.advanced.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InterfaceProxyConfig {

    /**
     * OrderControllerInterfaceProxy 이 녀석을 반환해줘야 속는다.
     * OrderControllerV1Impl 이 녀석은 프록시에 해당하는 orderService을 호출해야 한다.
     */
    @Bean
    public OrderControllerV1 orderController(LogTrace logTrace) {
        OrderControllerV1Impl controllerImpl = new OrderControllerV1Impl(orderService(logTrace));
        return new OrderControllerInterfaceProxy(controllerImpl, logTrace);
    }

    @Bean
    public OrderServiceV1 orderService(LogTrace logTrace) {
        OrderServiceV1Impl serviceImpl = new OrderServiceV1Impl(orderRepository(logTrace));
        return new OrderServiceInterfaceProxy(serviceImpl, logTrace);
    }

    @Bean
    public OrderRepositoryV1 orderRepository(LogTrace logTrace) {
        OrderRepositoryV1Impl repositoryImpl = new OrderRepositoryV1Impl();
        return new OrderRepositoryInterfaceProxy(repositoryImpl, logTrace);
    }

}
