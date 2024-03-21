package core.advanced.cglib;


import core.advanced.cglib.code.TimeMethodInterceptor;
import core.advanced.common.service.ConcreteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

@Slf4j
public class CglibTest {

    @Test
    void cglib() {
        ConcreteService target = new ConcreteService();

        /**
         * Enhancer 이것이 필요하다..? CGLIB을 만드는 코드?
         * 구체 클래스를 기반으로 ConcreteService 을 상속받은 프록시를 만들어야 한다.
         * setCallback()은 매개변수로 callback을 받는데.. 왜 TimeMethodInterceptor 가 넘어가지?
         * TimeMethodInterceptor 의 인터페이스인 MethodInterceptor 가 Callback 인터페이스를 상속받고 있다.
         * enhancer.create()으로 프록시를 만들수 있고 ConcreteService 로 캐스팅이 가능하다.
         */
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ConcreteService.class);
        enhancer.setCallback(new TimeMethodInterceptor(target));
        ConcreteService proxy = (ConcreteService) enhancer.create();
        log.info("targetClass={}", target.getClass());
        /**
         * proxyClass=class core.advanced.common.service.ConcreteService$$EnhancerByCGLIB$$b89f21f8
         */
        log.info("proxyClass={}", proxy.getClass());

        proxy.call();

    }
}
