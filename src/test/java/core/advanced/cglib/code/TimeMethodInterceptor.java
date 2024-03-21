package core.advanced.cglib.code;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * TimeMethodInterceptor은 MethodInterceptor을 구현해서 CGLIB 프록시의 실행 로직을 정의한다.
 */
@Slf4j
public class TimeMethodInterceptor implements MethodInterceptor {

    /**
     * 항상 프록시는 내가 호출할 대상이 필요하다.
     */
    private final Object target;

    public TimeMethodInterceptor(Object target) {
        this.target = target;
    }

    /**
     * MethodProxy 이것을 쓰는게 조금 더 빠르다고 한다? 적혀있다.
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();

        Object result = methodProxy.invoke(target, args);

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeProxy 종료 resultTime={}", resultTime);
        return result;
    }
}
