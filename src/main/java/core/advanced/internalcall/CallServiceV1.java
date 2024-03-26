package core.advanced.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV1 {

    private CallServiceV1 callServiceV1;

    /**
     * 생성자로 자기자신을 받으면 안된다. 순환 사이클 문제가 발생한다. 닭이 먼저냐 달걀이 먼저냐 문제다.
     * setter 로 의존관계를 주입하면 된다. 스프링은 생성을 다 하는 단계가 있고 setter 을 주입하는 단계가 분리되어 있다.
     * 생성이 다 끝나고 setter 로 생성된 자기자신을 주입할 수 있다. 이때 프록시가 주입된다.
     */
    @Autowired
    public void setCallServiceV1(CallServiceV1 callServiceV1) {
        this.callServiceV1 = callServiceV1;
    }

    public void external() {
        log.info("call external");
        callServiceV1.internal(); //외부 메서드 호출
    }

    public void internal() {
        log.info("call internal");
    }

}
