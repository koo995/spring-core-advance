package core.advanced.internalcall;

import core.advanced.internalcall.aop.CallLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(CallLogAspect.class)
@SpringBootTest
class CallServiceV0Test {

    /**
     * 이 녀석은 프록시일 것이다. aop 가 먹힐테니까
     */
    @Autowired CallServiceV0 callServiceV0;

    /**
     * 지금 aop 는 external() internal() 둘다 대상이 될 것이다.
     * core.advanced.internalcall..*.*(..) 이렇게 걸었으니까.
     * external() 을 호출할때는
     * aop=void core.advanced.internalcall.CallServiceV0.external() 이 녀석이 찍히고 실행된다.
     * 그러나 external() 내부의 internal() 이 호출될 때는 aop 가 찍히지 않았다.
     */
    @Test
    void external() {
        callServiceV0.external();
    }

    /**
     * internal() 만 따로 호출하면 aop 가 찍힌다.
     */
    @Test
    void internal() {
        callServiceV0.internal();
    }
}