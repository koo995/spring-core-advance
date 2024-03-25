package core.advanced.order.member;


import core.advanced.order.member.annotation.ClassAop;
import core.advanced.order.member.annotation.MethodAop;
import org.springframework.stereotype.Component;

@ClassAop
@Component
public class MemberServiceImpl implements MemberService {

    @Override
    @MethodAop("test value")
    public String hello(String param) {
        return "ok";
    }

    /**
     * 오버라이딩 안하는 것
     */
    public String internal(String param) {
        return "ok";
    }
}
