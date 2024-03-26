package core.advanced.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    /**
     * 이렇게 하면 pointcut 의 표현식을 여러군데서 쓸 수 있다. private 을 public 으로 변경한다면
     * 메서드 이름과 파라미터를 합쳐서 pointcut signature 라 한다. 의미 부여 가능
     * 반환타입은 void 여야 한다.
     */
    @Pointcut("execution(* core.advanced.order..*(..))") // pointcut expression
    public void allOrder(){} //pointcut signature

    /**
     * 트랜잭션을 걸기위한 위치  클래스(또는 인터페이스)명이 Service 인 녀석들을 잡음
     */
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService(){}

    /**
     * 위의 두개를 조합한 것도 만들 수 있다.
     */
    @Pointcut("allOrder() && allService()")
    public void orderAndService() {}
}
