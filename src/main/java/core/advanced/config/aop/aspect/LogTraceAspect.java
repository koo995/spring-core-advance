package core.advanced.config.aop.aspect;


import core.advanced.trace.TraceStatus;
import core.advanced.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * 참고로 @Aspect 하나있는데 execution 메서드를 2개 이상만들면 advisor 가 2개 이상 만들어 진다. 단 프록시는 하나.
 */
@Slf4j
@Aspect
public class LogTraceAspect {

    private final LogTrace logTrace;

    public LogTraceAspect(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    /**
     * 지금은 그냥 Around 으로 포인트컷
     * public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
     *     여기에 advice 로직이 들어간다고 생각
     * }
     * 위의 두개를 합쳐서 advisor 라고 생각하자.
     */
    @Around("execution(* core.advanced.app..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        TraceStatus status = null;
        try {
            /**
             * joinPoint.getTarget() 실제 호출 대상
             * joinPoint.getArgs() 전달인자
             * joinPoint.getSignature() join point 시그너처
             * 와 같은 정보를 가져올 수 있다.
             */
            String message = joinPoint.getSignature().toShortString();
            status = logTrace.begin(message);

            /**
             * 로직 호출
             * MethodInvocation invocation 의 proceed() 와 이름이 같네? 유사한 기능이다.
             * 실제 호출 대상 target 을 호출한다.
             */
            Object result = joinPoint.proceed();

            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
