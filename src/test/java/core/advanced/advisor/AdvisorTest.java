package core.advanced.advisor;

import core.advanced.common.advice.TimeAdvice;
import core.advanced.common.service.ServiceImpl;
import core.advanced.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import java.lang.reflect.Method;

@Slf4j
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

    @Test
    @DisplayName("직접 만든 포인트컷")
    void advisorTest2() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new MyPointcut(), new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        System.out.println("------------------------------");
        proxy.find();
    }

    /**
     * 사실 이것 말고도 어마어마한 포인트컷이 있다.
     * 실제로는 aspectJ 관련 포인트컷을 쓸 것이다?
     * 지금은 최대한 쉬운 것을 써본다.
     * NameMatchMethodPointcut 메서드 이름을 기반으로 매칭한다. 내부에서는 "PatternMatchUtils"를 사용한다.
     * 그외...
     * JdkRegexpMethodPointcut jdk 정규 표현식을 기반으로 포인트컷을 매칭한다.
     * TruePointcut
     * AnnotationMatchingPointcut 애노테이션으로 매칭한다.
     * AspectJExpressionPointcut aspectJ 표현식으로 매칭한다. << 가장 중요한 것이다. 이후 AOP 에서 자세하게 설명
     */
    @Test
    @DisplayName("스프링이 제공하는 포인트컷")
    void advisorTest3() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);

        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("save");

        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

    /**
     * 직접 만든 pointcut
     */
    static class MyPointcut implements Pointcut {

        @Override
        public ClassFilter getClassFilter() {
            return ClassFilter.TRUE;
        }

        @Override
        public MethodMatcher getMethodMatcher() {
            return new MyMethodMatcher();
        }
    }

    static class MyMethodMatcher implements MethodMatcher {

        /**
         * 메서드명이 "save"일때만 적용하기 위함.
         */
        private String matchName = "save";

        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            boolean result = method.getName().equals(matchName);
            log.info("포인트컷 호출 method={} targetClass={}", method.getName(), targetClass);
            log.info("포인트컷 결과 result={}", result);
            return result;
        }

        /**
         * 이 아래는 크게 중요한건 아니다. 참고만 하자.
         */
        @Override
        public boolean isRuntime() {
            return false;
        }

        @Override
        public boolean matches(Method method, Class<?> targetClass, Object... args) {
            return false;
        }
    }
}
