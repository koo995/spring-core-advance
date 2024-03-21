package core.advanced.jdkdynamic.code;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * TimeInvocationHandler은 InvocationHandler을 구현한다. 이렇게해서 jdk 동적 프록시에 적용할 공통 로직을 개발할 수 있다.
 */
@Slf4j
public class TimeInvocationHandler implements InvocationHandler {

    /**
     * 굉장히 범용으로 쓰기 위함.
     * target 은 동적 프록시가 호출할 대사아.
     */
    private final Object target;

    public TimeInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();

        /**
         * 어떤 메서드가 호출될지 method가 넘어온다.
         * args가 넘어올 수 있다.
         */
        Object result = method.invoke(target, args);

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeProxy 종료 resultTime={}", resultTime);
        return result;
    }
}
