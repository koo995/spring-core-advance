package core.advanced;


import core.advanced.order.OrderRepository;
import core.advanced.order.OrderService;
import core.advanced.order.aop.AspectV1;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * "@Aspect" 는 애스팩트라는 표식이지 컴포넌트 스캔이 되는 것은 아니다.
 * @Bean 으로 등록을 해야 되는데... 강의에서 하나씩 바꿔갈 것이기 때문에 @Import 을 이용한다. 그러면 스프링 빈으로 등록된다.
 * 참고로 스프링 빈으로 등록하는 방법은
 * 1. "@Bean"
 * 2. "@Component"
 * 3. "@Import" << 이것은 주로 설정 파일을 추가할 때 사용('@Configuration')
 */
@Slf4j
@Import({AspectV1.LogAspect.class, AspectV1.TransactionAspect.class})  // 따로 등록을 해줘야 한다.
@SpringBootTest
public class AopTest {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    /**
     * aop 가 적용이 되었는지 안되었는지 확인하기 위함.
     */
    @Test
    void aopInfo() {
        log.info("isAopProxy, orderService={}", AopUtils.isAopProxy(orderService));
        log.info("isAopProxy, orderRepository={}", AopUtils.isAopProxy(orderRepository));
    }

    @Test
    void success() {
        orderService.orderItem("itemA");
    }

    @Test
    void exception() {
        Assertions.assertThatThrownBy(() -> orderService.orderItem("ex"))
                .isInstanceOf(IllegalStateException.class);
    }

}
