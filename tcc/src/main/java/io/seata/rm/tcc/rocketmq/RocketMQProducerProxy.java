package io.seata.rm.tcc.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RocketMQProducerProxy implements InvocationHandler {
    public static Logger LOGGER = LoggerFactory.getLogger(RocketMQScanner.class);

    private final DefaultMQProducer target;

    public RocketMQProducerProxy(DefaultMQProducer target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // TODO 仅代理half方法
        LOGGER.info("invoke method: [{}]", method.getName());
        return method.invoke(target);
    }

    public static Object getProxyInstance(DefaultMQProducer producer) {
        return Proxy.newProxyInstance(producer.getClass().getClassLoader(), producer.getClass().getInterfaces(), new RocketMQProducerProxy(producer));
    }
}
