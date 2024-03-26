package core.advanced.exam.aop;

import core.advanced.exam.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class RetryAspect {

    /**
     * 매개변수로 Retry 을 받고 싶다. 위에 import 로 받으면 된다.
     */
    @Around("@annotation(retry)")
    public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        log.info("[retry] {} retry={}", joinPoint.getSignature(), retry);

        int maxRetry = retry.value();
        // 리턴횟수가 다 돈다음 던지기 위해서 받아놔야 한다.
        Exception exceptionHolder = null;

        /**
         * 예외가 터져서 재시도가 되는 경우 repository 안에 있는 static 변수의 값이 6이 되니까 다시 실행이 될 것이다.
         * 예외가 발생해서 재시도를 maxRetry 만큼 했는데도 안되면 예외를 던진다.
         */
        for (int retryCount = 1; retryCount <= maxRetry; retryCount++) {
            try {
                log.info("[retry] try count={}/{}", retryCount, maxRetry);
                return joinPoint.proceed();
            } catch (Exception e) {
                exceptionHolder = e;
            }
        }
        throw exceptionHolder;
    }
}
