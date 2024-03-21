package core.advanced.common.service;

import lombok.extern.slf4j.Slf4j;

/**
 * 이 녀석은 인터페이스 없이 단순하게 메서드 하나만 가진 경우
 */
@Slf4j
public class ConcreteService {
    public void call() {
        log.info("ConcreteService 호출");
    }
}
