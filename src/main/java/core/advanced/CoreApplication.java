package core.advanced;

import core.advanced.config.AppV1Config;
import core.advanced.config.AppV2Config;
import core.advanced.config.v1_proxy.InterfaceProxyConfig;
import core.advanced.trace.logtrace.LogTrace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

//@Import(value = {AppV1Config.class, AppV2Config.class})
@Import(InterfaceProxyConfig.class)
@SpringBootApplication(scanBasePackages = "core.advanced.app.v3")
public class CoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }
}
