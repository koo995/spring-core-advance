package core.advanced.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV2 {

//
    /**
     * private final ApplicationContext applicationContext 이것과 생성자를 이용한 방식. 가장 무식한 방법이다. 너무 거대하다.
     * ObjectProvider 을 이용해 보자. 프로바이더에서 오브젝트를 꺼내는 것 자체가 목적이다. 지연으로 조회가 가능하다.
     */
    private final ObjectProvider<CallServiceV2> callServiceProvider;

    public CallServiceV2(ObjectProvider<CallServiceV2> callServiceProvider) {
        this.callServiceProvider = callServiceProvider;
    }

    public void external() {
        log.info("call external");
//        CallServiceV2 callServiceV2 = applicationContext.getBean(CallServiceV2.class);
        CallServiceV2 callServiceV2 = callServiceProvider.getObject();
        callServiceV2.internal(); //외부 메서드 호출
    }

    public void internal() {
        log.info("call internal");
    }
}
