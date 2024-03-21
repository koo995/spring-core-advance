package core.advanced.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

@Slf4j
public class reflectionTest {


    /**
     * 호출하는 메서드만 다르고 나머지는 똑같다.
     * 여기서 공통 로직1과 공통 로직 2를 하나의 메서드로 뽑아서 합칠 수 있을까?
     * 쉽지않다. 중간에 호출하는 메서드가 다르기 때문이다.
     * target.callA(), target.callB() 이 부분만 동적으로 처리할 수 있다면 문제를 해결할 수 있을 듯 하다.
     * 이럴 때 사용하는 기술이 바로 리플렉션. (참고로 람다를 사용해서 공통화 하는 것도 가능하다.)
     */
    @Test
    void reflection0() {
        Hello target = new Hello();

        // 공통 로직1 시작
        log.info("start");
        String result1 = target.callA();
        log.info("result={}", result1);

        // 공통 로직2 시작
        log.info("start");
        String result2 = target.callB();
        log.info("result={}", result2);
    }

    /**
     * Hello라는 클래스가 reflectionTest안에 있으니까 $ 표시를 사용한다.
     * 모든 정보를 "문자"를 이용하여 얻었다 -> 문자는 파라미터로 얼마든지 외부에서 바꿔치기가 가능하다.
     * 메서드 정보를 획득해서 메서드를 호출하면 어떤 효과가 있을까? 핵심은 클래스나 메서드 정보를 동적으로 변경할 수 있다는 점이다.
     * 기존의 callA() callB() 메서드를 직접 호출하는 부분이 Method 로 추상화가 되었다. 공통부분으로 만들수가 있게 되었다.
     */
    @Test
    void reflection1() throws Exception {
        // 클래스 정보를 획득한다.
        Class classHello = Class.forName("core.advanced.jdkdynamic.reflectionTest$Hello");

        Hello target = new Hello();

        // callA 메서드 정보를 획득한다.
        Method methodCallA = classHello.getMethod("callA");
        Object result1 = methodCallA.invoke(target);
        log.info("result1={}", result1);

        // callB 메서드 정보를 획득한다.
        Method methodCallB = classHello.getMethod("callB");
        Object result2 = methodCallB.invoke(target);
        log.info("result2={}", result2);
    }

    @Test
    void reflection2() throws Exception {
        // 클래스 정보를 획득한다.
        Class classHello = Class.forName("core.advanced.jdkdynamic.reflectionTest$Hello");

        Hello target = new Hello();

        // callA 메서드 정보를 획득한다.
        Method methodCallA = classHello.getMethod("callA");
        dynamicCall(methodCallA, target);

        // callB 메서드 정보를 획득한다.
        Method methodCallB = classHello.getMethod("callB");
        dynamicCall(methodCallB, target);
    }

    private void dynamicCall(Method method, Object target) throws Exception {
        log.info("start");
        Object result = method.invoke(target);
        log.info("result={}", result);
    }

    @Slf4j
    static class Hello {
        public String callA() {
            log.info("callA");
            return "A";
        }

        public String callB() {
            log.info("callB");
            return "B";
        }
    }
}
