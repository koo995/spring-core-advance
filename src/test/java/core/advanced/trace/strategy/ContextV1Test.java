package core.advanced.trace.strategy;


import core.advanced.trace.strategy.code.strategy.ContextV1;
import core.advanced.trace.strategy.code.strategy.Strategy;
import core.advanced.trace.strategy.code.strategy.StrategyLogic1;
import core.advanced.trace.strategy.code.strategy.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV1Test {

    @Test
    void strategyV0() {
        logic1();
        logic2();
    }

    private void logic1() {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        log.info("비즈니스 로직1 실행");
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

    private void logic2() {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        log.info("비즈니스 로직2 실행");
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

    /**
     * 전략 패턴 사용
     * 필드에 전략을 보관하는 방식
     * 스프링에서 의존관계 주입에서 사용하는 방식이 바로 전략 패턴이다.
     * 템플릿 메서드 패턴에서는 부모클래스인 추상메서드가 바뀌면 자식클래스가 영향을 받는다. 상속관계이기 때문
     * 이제 그럴 염려가 줄어든다. 인터페이스에 의존하니 위임 관계로 변했다.
     * 변하지 않는 부분을 context에 두고 변하는 부분을 strategy를 구현해서 만든다.
     * 그리고 context의 내부필드에 strategy를 주입해서 사용했다.
     * 선 조립, 후 실행 방식이다. 조립이 끝난 후 execute만 실행하면 된다.
     * 스프링으로 애플리케이션을 개발할 때 애플리케이션 로딩 시점에 의존관계 주입을 통해 필요한 의존관계를 모두 맺어두고 난 다음에 실제 요청을 처리하는 것과 같은 원리다.
     * 단점은 조립한 이후에는 전략을 변경하기가 번거롭다는 점이다.
     */
    @Test
    void strategyV1() {
        StrategyLogic1 strategyLogic1 = new StrategyLogic1();
        ContextV1 context1 = new ContextV1(strategyLogic1);
        context1.execute();

        StrategyLogic2 strategyLogic2 = new StrategyLogic2();
        ContextV1 context2 = new ContextV1(strategyLogic2);
        context2.execute();
    }

    @Test
    void strategyV2() {
        Strategy strategyLogic1 = new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직1 실행");
            }
        };
        ContextV1 context1 = new ContextV1(strategyLogic1);
        log.info("strategyLogic1={}", strategyLogic1.getClass());
        context1.execute();

        Strategy strategyLogic2 = new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직2 실행");
            }
        };
        ContextV1 context2 = new ContextV1(strategyLogic2);
        log.info("strategyLogic2={}", strategyLogic2.getClass());
        context2.execute();
    }

    /**
     * strategyV2에서 변수를 굳이 두지 않고, Strategy을 안에 바로 넣어버렸다.
     */
    @Test
    void strategyV3() {
        ContextV1 context1 = new ContextV1(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직1 실행");
            }
        });
        context1.execute();

        ContextV1 context2 = new ContextV1(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직2 실행");
            }
        });
        context2.execute();
    }

    /**
     * strategyV3에서 추상메서드가 1개 이니까 람다식으로 변경
     */
    @Test
    void strategyV4() {
        ContextV1 context1 = new ContextV1(() -> log.info("비즈니스 로직1 실행"));
        context1.execute();

        ContextV1 context2 = new ContextV1(() -> log.info("비즈니스 로직2 실행"));
        context2.execute();
    }
}