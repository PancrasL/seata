package io.seata.rm.tcc.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import io.seata.core.context.RootContext;

public class RocketMQScanner implements BeanPostProcessor {
    public static Logger LOGGER = LoggerFactory.getLogger(RocketMQScanner.class);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof DefaultMQProducer && RootContext.getXID() != null) {
            LOGGER.info("转换为DefaultMQProducer的代理类");
            return RocketMQProducerProxy.getProxyInstance((DefaultMQProducer) bean);
        }
        return bean;
    }
}
