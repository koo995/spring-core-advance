package core.advanced.config.postprocessor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 특정 패키지 하위에 있는 Bean 들만 proxy 로 만들려고 한다.
 */
@RequiredArgsConstructor
@Slf4j
public class PackageLogTracePostProcessor implements BeanPostProcessor {

    private final String basePackage;
    /**
     * 어떤 advisor 을 사용할지 정해야 한다.
     */
    private final Advisor advisor;


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("param beanName={} bean={}", beanName, bean.getClass());

        /**
         * 프록시 적용 대상 여부 체크
         * 프록시 적용 대상이 아니면 원본을 그대로 진행
         */
        String packageName = bean.getClass().getPackageName();
        if (!packageName.startsWith(basePackage)) {
            return bean;
        }

        /**
         * 프록시 대상이면 프록시를 만들어서 반환
         * ProxyFactory 에는 타겟이 들어가야하니가 bean 을 넣어준다.
         */
        ProxyFactory proxyFactory = new ProxyFactory(bean);
        proxyFactory.addAdvisor(advisor);

        /**
         * 캐스팅 없이 그냥 Object 로 꺼낸다...?
         */
        Object proxy = proxyFactory.getProxy();
        log.info("create proxy: target={} proxy={}", bean.getClass(), proxy.getClass());
        return proxy;
    }
}
