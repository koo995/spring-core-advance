package core.advanced.trace.strategy.code.strategy;

import lombok.extern.slf4j.Slf4j;

/**
 * 필드에 전략을 보관하는 방식
 * 스프링에서 의존관계 주입에서 사용하는 방식이 바로 전략 패턴이다.
 * 템플릿 메서드 패턴에서는 부모클래스인 추상메서드가 바뀌면 자식클래스가 영향을 받는다. 상속관계이기 때문
 * 이제 그럴 염려가 줄어든다. 인터페이스에 의존하니 위임 관계로 변했다.
 */
@Slf4j
public class ContextV1 {

    private Strategy strategy;

    /**
     * 전략을 주입받는다.
     * @param strategy
     */
    public ContextV1(Strategy strategy) {
        this.strategy = strategy;
    }

    public void execute() {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        strategy.call(); //위임
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }
}
