package core.advanced.config.autoproxy;


import core.advanced.config.AppV1Config;
import core.advanced.config.AppV2Config;
import core.advanced.config.proxyfactory.advice.LogTraceAdvice;
import core.advanced.trace.logtrace.LogTrace;
import org.springframework.aop.Advisor;
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

    @Bean
    public Advisor advisor1(LogTrace logTrace) {
        //pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");
        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
