package core.advanced.pointcut;

import core.advanced.order.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

@Slf4j
public class ExecutionTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    /**
     * 테스트가 실행될때마다 뽑아서 값을 넣어두기 위함
     * 리프렉션을 이용하는 것인데, 클래스정보를 가져오고 거기서 메서드 정보를 가져오는 구나 그런데 String.class 는 뭐지?
     * 클래스 내에 String 타입의 매개변수를 받는 hello 라는 이름의 메소드를 찾습니다.
     */
    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod() {
        //public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
        log.info("helloMethod={}", helloMethod);
    }
}
