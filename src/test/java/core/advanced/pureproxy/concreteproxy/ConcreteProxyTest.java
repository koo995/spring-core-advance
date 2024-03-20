package core.advanced.pureproxy.concreteproxy;


import core.advanced.pureproxy.concreteproxy.code.ConcreteClient;
import core.advanced.pureproxy.concreteproxy.code.ConcreteLogic;
import org.junit.jupiter.api.Test;

public class ConcreteProxyTest {

    @Test
    void noProxy() {
        ConcreteLogic concreteLogic = new ConcreteLogic();
        ConcreteClient client = new ConcreteClient(concreteLogic);
        client.execute();
    }
}
