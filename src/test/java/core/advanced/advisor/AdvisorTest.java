package core.advanced.advisor;

import core.advanced.common.advice.TimeAdvice;
import core.advanced.common.service.ServiceImpl;
import core.advanced.common.service.ServiceInterface;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

public class AdvisorTest {

    @Test
    void advisorTest1() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        /**
         * pointcut 과 advice 두가지를 advisor 에 넣어준다.
         * Pointcut.TRUE 은 항상 true을 반환하는 포인트컷이다.
         */
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        /**
         * 모두 타임이 적용된 것을 볼 수 있다.
         */
        proxy.save();
        proxy.find();
    }
}
