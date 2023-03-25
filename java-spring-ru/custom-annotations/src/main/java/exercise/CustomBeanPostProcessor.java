package exercise;

import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomBeanPostProcessor.class);
    private static final Map<Object, String> BEAN_LOG_LEVELS = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Inspect.class)) {
            String logLevel = bean.getClass().getAnnotation(Inspect.class).level();
            BEAN_LOG_LEVELS.put(bean, logLevel);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (BEAN_LOG_LEVELS.containsKey(bean)) {
            return Proxy.newProxyInstance(
                    bean.getClass().getClassLoader(),
                    bean.getClass().getInterfaces(),
                    (proxy, method, args) -> {
                       Object result = method.invoke(bean, args);
                       String message = "Was called method: " + method.getName() +"()" + " with arguments: " +
                               Arrays.toString(args);
                       String logLevel = BEAN_LOG_LEVELS.get(bean);
                       switch (logLevel) {
                           case "info" -> LOGGER.info(message);
                           case "debug" -> LOGGER.debug(message);
                           default -> throw new RuntimeException("unknown log level");
                       }
                       return result;
                    });
        }
        return bean;
    }
}
