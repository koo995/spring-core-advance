package core.advanced.jdkdynamic;

import core.advanced.jdkdynamic.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

@Slf4j
public class JdkDynamicProxyTest {

    @Test
    void dynamicA() {
        /**
         * 여기에다 어떤 프록시를 넣고 싶은 것이다.
         */
        AInterface target = new AInterfaceImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        /**
         * jdk 상에서 프록시를 생성하는 방법이다. 인터페이스는 여러개를 구현할 수 있으니 배열을 적용한다.
         * handler에는 프록시가 호출할 로직을 넣어준다.
         */
        AInterface proxy = (AInterface) Proxy.newProxyInstance(AInterface.class.getClassLoader(), new Class[]{AInterface.class}, handler);

        /**
         * AInterface에 call()이 있으니 호출한다.
         * call()이 호출되면 handler 안에 있는 invoke()메서드가 호출된다. invoke()안의 Method는 call()이라는 것이 넘어간다.
         * 즉 동적 프록시는 handler의 invoke()을 호출한다.
         */
        proxy.call();
        log.info("targetClass={}", target.getClass());
        /**
         * jdk.proxy3.$Proxy11 로 나타난다. 실행할때 만들어진다.
         */
        log.info("proxyClass={}", proxy.getClass());
    }

    @Test
    void dynamicB() {
        BInterface target = new BInterfaceImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        BInterface proxy = (BInterface) Proxy.newProxyInstance(BInterface.class.getClassLoader(), new Class[]{BInterface.class}, handler);

        proxy.call();
        log.info("targetClass={}", target.getClass());
        /**
         * jdk.proxy3.$Proxy12 로 나타난다. 동일한 jvm상에서 실행할때 순차적으로 만들어져서 그렇다? 중요한건 아님
         */
        log.info("proxyClass={}", proxy.getClass());
    }
}
