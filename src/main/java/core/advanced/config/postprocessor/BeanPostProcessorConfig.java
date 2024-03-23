package core.advanced.config.postprocessor;


import core.advanced.config.AppV1Config;
import core.advanced.config.AppV2Config;
import core.advanced.config.proxyfactory.advice.LogTraceAdvice;
import core.advanced.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * v1 v2을 수동으로 등록해야 하는데.. 여기서 그냥 넣어버린다. v3은 컴포넌트 스캔으로 자동으로 등록될것
 * @Import({AppV1Config.class, AppV2Config.class}) 이것을 CoreApplication 에 넣어도 되지만 그냥 편의상 여기다 함.
 */
@Slf4j
@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class BeanPostProcessorConfig {

    @Bean
    public PackageLogTracePostProcessor logTracePostProcessor(LogTrace logTrace) {
        return new PackageLogTracePostProcessor("core.advanced.app", getAdvisor(logTrace));
    }

    private Advisor getAdvisor(LogTrace logTrace) {
        //pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");
        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
