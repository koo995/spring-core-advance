package core.advanced.config.aop;


import core.advanced.config.AppV1Config;
import core.advanced.config.AppV2Config;
import core.advanced.config.aop.aspect.LogTraceAspect;
import core.advanced.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AopConfig {

    /**
     * LogTraceAspect 가 advice advisor 로 변환이 되어서 들어간다.
     */
    @Bean
    public LogTraceAspect logTraceAspect(LogTrace logTrace) {
        return new LogTraceAspect(logTrace);
    }
}
