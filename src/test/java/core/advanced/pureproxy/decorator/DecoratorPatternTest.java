package core.advanced.pureproxy.decorator;

import core.advanced.pureproxy.decorator.code.Component;
import core.advanced.pureproxy.decorator.code.DecoratorPatternClient;
import core.advanced.pureproxy.decorator.code.RealComponent;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class DecoratorPatternTest {

    @Test
    void noDecorator() {
        Component realComponent = new RealComponent();
        DecoratorPatternClient client = new DecoratorPatternClient(realComponent);
        client.execute();
    }
}
