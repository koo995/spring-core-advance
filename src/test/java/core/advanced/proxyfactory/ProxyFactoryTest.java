package core.advanced.proxyfactory;

import core.advanced.common.advice.TimeAdvice;
import core.advanced.common.service.ConcreteService;
import core.advanced.common.service.ServiceImpl;
import core.advanced.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

import static org.assertj.core.api.Assertions.*;

@Slf4j
public class ProxyFactoryTest {

    @DisplayName("인터페이스가 있으면 jdk 동적 프록시 사용")
    @Test
    void interfaceProxy() throws Exception {
        // given
        ServiceInterface target = new ServiceImpl();
        /**
         * 타겟넣으면서 프록시 팩토리를 만들면 된다.
         */
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        log.info("targetClass={}", target.getClass());
        /**
         * proxyClass=class jdk.proxy3.$Proxy12 >> jdk 동적 프록시가 생성되었다.
         */
        log.info("proxyClass={}", proxy.getClass());
        proxy.save();

        /**
         * 프록시 팩토리로 프록시가 잘 적용되었는지 확인하려면 다음 기능을 사용하면 된다.
         * 프록시 팩토리를 쓸때만 AopUtils 을 쓸 수 있다.
         */
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
        assertThat(AopUtils.isCglibProxy(proxy)).isFalse();
    }

    @DisplayName("구체클래스만 있으면 CGLIB 사용")
    @Test
    void concreteProxy() throws Exception {
        // given
        ConcreteService target = new ConcreteService();
        /**
         * 타겟넣으면서 프록시 팩토리를 만들면 된다.
         */
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());
        ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();
        log.info("targetClass={}", target.getClass());
        /**
         * proxyClass=class jdk.proxy3.$Proxy12 >> jdk 동적 프록시가 생성되었다.
         */
        log.info("proxyClass={}", proxy.getClass());
        proxy.call();

        /**
         * 프록시 팩토리로 프록시가 잘 적용되었는지 확인하려면 다음 기능을 사용하면 된다.
         * 프록시 팩토리를 쓸때만 AopUtils 을 쓸 수 있다.
         */
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }

    @DisplayName("ProxyTargetClass 옵션을 사용하면 인터페이스가 있어도 CGLIB 을 사용하고, 클래스 기반 프록시 사용")
    @Test
    void proxyTargetClass() throws Exception {
        // given
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        /**
         * 인터페이스가 있던 없던 항상 CGLIB을 사용하고 싶다면
         * 실무에서도 등장하기 때문에 중요하다.
         */
        proxyFactory.setProxyTargetClass(true);
        proxyFactory.addAdvice(new TimeAdvice());
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        log.info("targetClass={}", target.getClass());
        /**
         * proxyClass=class jdk.proxy3.$Proxy12 >> jdk 동적 프록시가 생성되었다.
         */
        log.info("proxyClass={}", proxy.getClass());
        proxy.save();

        /**
         * 프록시 팩토리로 프록시가 잘 적용되었는지 확인하려면 다음 기능을 사용하면 된다.
         * 프록시 팩토리를 쓸때만 AopUtils 을 쓸 수 있다.
         */
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }

}
