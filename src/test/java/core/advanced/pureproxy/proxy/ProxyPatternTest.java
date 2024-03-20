package core.advanced.pureproxy.proxy;

import core.advanced.pureproxy.proxy.code.CacheProxy;
import core.advanced.pureproxy.proxy.code.ProxyPatternClient;
import core.advanced.pureproxy.proxy.code.RealSubject;
import org.junit.jupiter.api.Test;

public class ProxyPatternTest {

    @Test
    void noProxyTest() {
        RealSubject realSubject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(realSubject);
        client.execute();
        client.execute();
        client.execute();
    }

    @Test
    void cacheProxyTest() {
        RealSubject realSubject = new RealSubject();
        CacheProxy cacheProxy = new CacheProxy(realSubject);
        /**
         real이 아니라 proxy을 넣어준다. ProxyPatternClient는 내부에 Subject라는 인터페이스만 의존하기 때문에 실제 객체인지 프록시인지 알지 못한다.
         접근을 제어한다고 볼 수 있다
         */
        ProxyPatternClient client = new ProxyPatternClient(cacheProxy);
        client.execute();
        client.execute();
        client.execute();
    }
}
