package core.advanced.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV2 {

    /**
     * JointPoint 을 써야한다. ProceedingJoinPoint 는 Around 에서만 쓰인다.
     * 실행은 나머지가 알아서 해준다. joinPoint.proceed(); 을 해줄 필요가 없다.
     * 심지어 joinPoint 의 정보도 필요없다면 굳이 매개변수로 받을 필요도 없다.
     */
    @Before("core.advanced.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[before] {}", joinPoint.getSignature());
    }

    /**
     * Around 을 썼을 때는 리턴값을 조절할 수 있고 값을 "바꿔(변경)"버릴 수 있지만
     * 여기서는 리턴값을 바꿔버릴 수는 없다. 조작하는 것은 가능.
     * result 의 타입을 변경하면 타입이 안맞을 경우 받을 수 없다. 타입이 되는 것들만 호출이 된다. Object 는 모든 것을 다 받을 수 있다.
     */
    @AfterReturning(value = "core.advanced.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {
        log.info("[return] {} return={}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(value = "core.advanced.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("[ex] {} message={}", joinPoint.getSignature(), ex.getMessage());
    }

    /**
     * 일반적으로 리소스를 해제하는데 사용된다.
     */
    @After(value = "core.advanced.order.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[after] {}", joinPoint.getSignature());
    }
}
