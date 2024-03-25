package core.advanced.order.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

@Slf4j
@Aspect
public class AspectV1 {


    @Aspect
    @Order(2)
    public static class LogAspect {
        /**
         * 패키지명까지 다 가져와야 한다.
         */
        @Around("core.advanced.order.aop.Pointcuts.allOrder()")
        public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[log] {}", joinPoint.getSignature()); //join point 시그니처
            return joinPoint.proceed();
        }
    }

    @Aspect
    @Order(1)
    public static class TransactionAspect {
        /**
         * core.advanced.order 패키지와 하위 패키지 이면서 클래스(또는 인터페이스) 이름 패턴이 *Service
         */
        @Around("core.advanced.order.aop.Pointcuts.orderAndService()")
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

}
