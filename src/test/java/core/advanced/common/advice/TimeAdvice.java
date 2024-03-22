package core.advanced.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * aopalliance 패키지의 MethodInterceptor을 써야한다. 주의
 * target을 항상 넣어줬는데 안넣어줘도 된다. 프록시팩토리에서 타겟을 이미 넣어주기 때문이다.
 */
@Slf4j
public class TimeAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();

        /**
         * invocation안에서 꺼내서 쓸수있다. proceed()에서 알아서 타겟을 찾아서 타겟에 있는 다음 실제를 인수를 넘기면서 호출해준다.
         */
        Object result = invocation.proceed();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeProxy 종료 resultTime={}", resultTime);
        return result;
    }
}
