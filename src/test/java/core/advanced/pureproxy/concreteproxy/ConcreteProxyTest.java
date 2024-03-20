package core.advanced.pureproxy.concreteproxy;


import core.advanced.pureproxy.concreteproxy.code.ConcreteClient;
import core.advanced.pureproxy.concreteproxy.code.ConcreteLogic;
import core.advanced.pureproxy.concreteproxy.code.TimeProxy;
import org.junit.jupiter.api.Test;

public class ConcreteProxyTest {

    @Test
    void noProxy() {
        ConcreteLogic concreteLogic = new ConcreteLogic();
        ConcreteClient client = new ConcreteClient(concreteLogic);
        client.execute();
    }

    /**
     * client -> timeProxy -> concreteLogin 의 의존관계를 만든다.
     */
    @Test
    void addProxy() {
        ConcreteLogic concreteLogic = new ConcreteLogic();
        TimeProxy timeProxy = new TimeProxy(concreteLogic);
        ConcreteClient client = new ConcreteClient(timeProxy);
        client.execute();
    }
}
