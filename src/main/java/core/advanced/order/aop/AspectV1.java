package core.advanced.order.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV1 {

    /**
     * 이렇게 하면 pointcut 의 표현식을 여러군데서 쓸 수 있다. private 을 public 으로 변경한다면
     * 메서드 이름과 파라미터를 합쳐서 pointcut signature 라 한다. 의미 부여 가능
     * 반환타입은 void 여야 한다.
     */
    @Pointcut("execution(* core.advanced.order..*(..))") // pointcut expression
    private void allOrder(){} //pointcut signature

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature()); //join point 시그니처
        return joinPoint.proceed();
    }
}
