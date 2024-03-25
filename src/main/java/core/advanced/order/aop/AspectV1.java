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

    /**
     * 트랜잭션을 걸기위한 위치  클래스(또는 인터페이스)명이 Service 인 녀석들을 잡음
     */
    @Pointcut("execution(* *..*Service.*(..))")
    private void allService(){}

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature()); //join point 시그니처
        return joinPoint.proceed();
    }

    /**
     * core.advanced.order 패키지와 하위 패키지 이면서 클래스(또는 인터페이스) 이름 패턴이 *Service
     */
    @Around("allOrder() && allService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }
}
