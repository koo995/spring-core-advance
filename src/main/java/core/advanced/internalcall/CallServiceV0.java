package core.advanced.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV0 {

    public void external() {
        log.info("call external");
        /**
         * 내부 메서드 호출(this.internal()) java 언어에서 대상을 지정하지 않으면
         * 앞에 자기 자신의 인스턴스를 뜻하는 this 가 붙게 된다. 지금은 생략
         * 프록시가 호출할때는 this.internal() 의 this 가 프록시 인스턴스이고,
         * 프록시의 proceed() 로 target 의 external() 이 호출되고 그 후 internal() 이 호출될때
         * 이 internal() 은 this.internal() 이고 이때의 this 는 실제 대상 객체(타겟)에 해당하는 인스턴스이다. 즉 프록시가 아니다.
         */
        internal();
    }

    public void internal() {
        log.info("call internal");
    }
}
