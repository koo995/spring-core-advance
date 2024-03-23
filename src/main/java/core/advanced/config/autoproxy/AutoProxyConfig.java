package core.advanced.config.autoproxy;


import core.advanced.config.AppV1Config;
import core.advanced.config.AppV2Config;
import core.advanced.config.proxyfactory.advice.LogTraceAdvice;
import core.advanced.trace.logtrace.LogTrace;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * advisor 만 등록하면 끝난다. 왜냐? 자동 프록시 생성기라는 빈후처리기는 스프링이 자동으로 등록해놓는다.
 * advisor 을 찾을테니 advisor 만 등록
 */
@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AutoProxyConfig {

//    @Bean
    public Advisor advisor1(LogTrace logTrace) {
        //pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        /**
         * request*, order* 로 걸었기 때문에...
         * WebMvcConfigurationSupport.requestMappingHandlerMapping()
         * AppV2Config.orderControllerV2()
         * 불 필요한 것들에도 프록시가 생성된다... 결국 스프링이 내부에서 사용하는 빈에도 메서드 이름에 'request' 라는 단어만 들어가 있으면
         * 프록시가 만들어지게 되고, advice 도 적용되는 것이다.
         * 결론적으로 매우 정밀한 포인트컷이 필요하다.
         */
        pointcut.setMappedNames("request*", "order*", "save*");
        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

//    @Bean
    public Advisor advisor2(LogTrace logTrace) {
        /**
         * pointcut 을 AspectJExpressionPointcut 을 이용한다.
         * setExpression 문법은 지금은 그냥 넘어가자. 여기에만 적용된다 생각.
         * 문제는 OrderControllerV1Impl.noLog() time=0ms 도 찍힌다.
         */
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* core.advanced.app..*(..))");
        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    /**
     * noLog() 처리함.
     */
    @Bean
    public Advisor advisor3(LogTrace logTrace) {
        //pointcut
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* core.advanced.app..*(..)) && !execution(* core.advanced.app..noLog(..))");
        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
