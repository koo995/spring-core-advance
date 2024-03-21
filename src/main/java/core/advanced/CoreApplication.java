package core.advanced;

import core.advanced.config.dynamicproxy.DynamicProxyFilterConfig;
import core.advanced.trace.logtrace.LogTrace;
import core.advanced.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(DynamicProxyFilterConfig.class)
@SpringBootApplication(scanBasePackages = "core.advanced.app.v3")
public class CoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }

    /**
     * basePackage 위치랑 떨어져 있는데 이건 적용되는 것인가.
     */
    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }
}
